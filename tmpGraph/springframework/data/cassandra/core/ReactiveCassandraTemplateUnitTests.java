/*
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.cassandra.core;

import static org.assertj.core.api.Assertions.*;





import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;




import org.springframework.cassandra.core.session.ReactiveResultSet;
import org.springframework.cassandra.core.session.ReactiveSession;
import org.springframework.data.cassandra.domain.Person;

import com.datastax.driver.core.ColumnDefinitions;
import com.datastax.driver.core.DataType;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.exceptions.NoHostAvailableException;

/**
 * Unit tests for {@link ReactiveCassandraTemplate}.
 *
 * @author Mark Paluch
 */

public class ReactiveCassandraTemplateUnitTests {

	@Mock ReactiveSession session;
	@Mock ReactiveResultSet reactiveResultSet;
	@Mock Row row;
	@Mock ColumnDefinitions columnDefinitions;

	@Captor ArgumentCaptor<Statement> statementCaptor;

	ReactiveCassandraTemplate template;

	@Before
	public void setUp() {

		template = new ReactiveCassandraTemplate(session);

		when(session.execute(any(Statement.class))).thenReturn(Mono.just(reactiveResultSet));
		when(row.getColumnDefinitions()).thenReturn(columnDefinitions);
	}

	@Test // DATACASS-335
	public void selectUsingCqlShouldReturnMappedResults() {

		when(reactiveResultSet.rows()).thenReturn(Flux.just(row));
		when(columnDefinitions.contains(anyString())).thenReturn(true);
		when(columnDefinitions.getType(anyInt())).thenReturn(DataType.ascii());

		when(columnDefinitions.getIndexOf("id")).thenReturn(0);
		when(columnDefinitions.getIndexOf("firstname")).thenReturn(1);
		when(columnDefinitions.getIndexOf("lastname")).thenReturn(2);

		when(row.getObject(0)).thenReturn("myid");
		when(row.getObject(1)).thenReturn("Walter");
		when(row.getObject(2)).thenReturn("White");

		StepVerifier.create(template.select("SELECT * FROM person", Person.class)) //
				.expectNext(new Person("myid", "Walter", "White")) //
				.verifyComplete();

		verify(session).execute(statementCaptor.capture());
		assertThat(statementCaptor.getValue().toString()).isEqualTo("SELECT * FROM person");
	}

	@Test // DATACASS-335
	public void selectShouldTranslateException() {

		when(reactiveResultSet.rows()).thenThrow(new NoHostAvailableException(Collections.emptyMap()));

		StepVerifier.create(template.select("SELECT * FROM person", Person.class)) //
				.consumeErrorWith(e -> {
					assertThat(e).hasRootCauseInstanceOf(NoHostAvailableException.class);
				}).verify();
	}

	@Test // DATACASS-335
	public void selectOneByIdShouldReturnMappedResults() {

		when(reactiveResultSet.rows()).thenReturn(Flux.just(row));
		when(columnDefinitions.contains(anyString())).thenReturn(true);
		when(columnDefinitions.getType(anyInt())).thenReturn(DataType.ascii());

		when(columnDefinitions.getIndexOf("id")).thenReturn(0);
		when(columnDefinitions.getIndexOf("firstname")).thenReturn(1);
		when(columnDefinitions.getIndexOf("lastname")).thenReturn(2);

		when(row.getObject(0)).thenReturn("myid");
		when(row.getObject(1)).thenReturn("Walter");
		when(row.getObject(2)).thenReturn("White");

		StepVerifier.create(template.selectOneById("myid", Person.class)) //
				.expectNext(new Person("myid", "Walter", "White")) //
				.verifyComplete();

		verify(session).execute(statementCaptor.capture());
		assertThat(statementCaptor.getValue().toString()).isEqualTo("SELECT * FROM person WHERE id='myid';");
	}

	@Test // DATACASS-335
	public void existsShouldReturnExistingElement() {

		when(reactiveResultSet.rows()).thenReturn(Flux.just(row));

		StepVerifier.create(template.exists("myid", Person.class)).expectNext(true).verifyComplete();

		verify(session).execute(statementCaptor.capture());
		assertThat(statementCaptor.getValue().toString()).isEqualTo("SELECT * FROM person WHERE id='myid';");
	}

	@Test // DATACASS-335
	public void existsShouldReturnNonExistingElement() {

		when(reactiveResultSet.rows()).thenReturn(Flux.empty());

		StepVerifier.create(template.exists("myid", Person.class)).expectNext(false).verifyComplete();

		verify(session).execute(statementCaptor.capture());
		assertThat(statementCaptor.getValue().toString()).isEqualTo("SELECT * FROM person WHERE id='myid';");
	}

	@Test // DATACASS-335
	public void countShouldExecuteCountQueryElement() {

		when(reactiveResultSet.rows()).thenReturn(Flux.just(row));
		when(row.getLong(0)).thenReturn(42L);
		when(columnDefinitions.size()).thenReturn(1);

		StepVerifier.create(template.count(Person.class)).expectNext(42L).verifyComplete();

		verify(session).execute(statementCaptor.capture());
		assertThat(statementCaptor.getValue().toString()).isEqualTo("SELECT count(*) FROM person;");
	}

	@Test // DATACASS-335
	public void insertShouldInsertEntity() {

		when(reactiveResultSet.wasApplied()).thenReturn(true);

		Person person = new Person("heisenberg", "Walter", "White");
		StepVerifier.create(template.insert(person)).expectNext(person).verifyComplete();

		verify(session).execute(statementCaptor.capture());
		assertThat(statementCaptor.getValue().toString())
				.isEqualTo("INSERT INTO person (firstname,id,lastname) VALUES ('Walter','heisenberg','White');");
	}

	@Test // DATACASS-335
	public void insertShouldTranslateException() {

		reset(session);
		when(session.execute(any(Statement.class)))
				.thenReturn(Mono.error(new NoHostAvailableException(Collections.emptyMap())));

		StepVerifier.create(template.insert(new Person("heisenberg", "Walter", "White"))) //
				.consumeErrorWith(e -> {

					assertThat(e).hasRootCauseInstanceOf(NoHostAvailableException.class);
				}).verify();
	}

	@Test // DATACASS-335
	public void insertShouldNotApplyInsert() {

		when(reactiveResultSet.wasApplied()).thenReturn(false);

		Person person = new Person("heisenberg", "Walter", "White");

		StepVerifier.create(template.insert(person)).verifyComplete();
	}

	@Test // DATACASS-335
	public void updateShouldUpdateEntity() {

		when(reactiveResultSet.wasApplied()).thenReturn(true);

		Person person = new Person("heisenberg", "Walter", "White");

		StepVerifier.create(template.update(person)).expectNext(person).verifyComplete();

		verify(session).execute(statementCaptor.capture());
		assertThat(statementCaptor.getValue().toString())
				.isEqualTo("UPDATE person SET firstname='Walter',lastname='White' WHERE id='heisenberg';");
	}

	@Test // DATACASS-335
	public void updateShouldNotApplyUpdate() {

		when(reactiveResultSet.wasApplied()).thenReturn(false);

		Person person = new Person("heisenberg", "Walter", "White");

		StepVerifier.create(template.update(person)).verifyComplete();
	}

	@Test // DATACASS-335
	public void deleteShouldRemoveEntity() {

		when(reactiveResultSet.wasApplied()).thenReturn(true);

		Person person = new Person("heisenberg", "Walter", "White");

		StepVerifier.create(template.delete(person)).expectNext(person).verifyComplete();

		verify(session).execute(statementCaptor.capture());
		assertThat(statementCaptor.getValue().toString()).isEqualTo("DELETE FROM person WHERE id='heisenberg';");
	}

	@Test // DATACASS-335
	public void deleteShouldNotApplyRemoval() {

		when(reactiveResultSet.wasApplied()).thenReturn(false);

		Person person = new Person("heisenberg", "Walter", "White");

		StepVerifier.create(template.delete(person)).verifyComplete();
	}

	@Test // DATACASS-335
	public void truncateShouldRemoveEntities() {

		StepVerifier.create(template.truncate(Person.class)).verifyComplete();

		verify(session).execute(statementCaptor.capture());
		assertThat(statementCaptor.getValue().toString()).isEqualTo("TRUNCATE person;");
	}
}
