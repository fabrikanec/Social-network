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
package org.springframework.cassandra.core;

import static org.assertj.core.api.Assertions.*;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import org.junit.Before;
import org.junit.Test;
import org.springframework.cassandra.core.session.DefaultBridgedReactiveSession;
import org.springframework.cassandra.core.session.ReactiveResultSet;
import org.springframework.cassandra.test.integration.AbstractKeyspaceCreatingIntegrationTest;

import com.datastax.driver.core.KeyspaceMetadata;
import com.datastax.driver.core.exceptions.SyntaxError;

/**
 * Integration tests for {@link DefaultBridgedReactiveSession}.
 *
 * @author Mark Paluch
 */
public class DefaultBridgedReactiveSessionIntegrationTests extends AbstractKeyspaceCreatingIntegrationTest {

	DefaultBridgedReactiveSession reactiveSession;

	@Before
	public void before() throws Exception {

		this.session.execute("DROP TABLE IF EXISTS users;");

		this.reactiveSession = new DefaultBridgedReactiveSession(this.session, Schedulers.elastic());
	}

	@Test // DATACASS-335
	public void executeShouldExecuteDeferred() throws Exception {

		String query = "CREATE TABLE users (\n" + "  userid text PRIMARY KEY,\n" + "  first_name text\n" + ");";

		Mono<ReactiveResultSet> execution = reactiveSession.execute(query);

		KeyspaceMetadata keyspace = getKeyspaceMetadata();

		assertThat(keyspace.getTable("users")).isNull();

		StepVerifier.create(execution).consumeNextWith(actual -> {

			assertThat(actual.wasApplied()).isTrue();
		}).verifyComplete();

		assertThat(keyspace.getTable("users")).isNotNull();
	}

	@Test // DATACASS-335
	public void executeShouldTransportExceptionsInMono() {
		StepVerifier.create(reactiveSession.execute("INSERT INTO dummy;")).expectError(SyntaxError.class).verify();
	}

	@Test // DATACASS-335
	public void executeShouldReturnRows() throws Exception {

		session.execute("CREATE TABLE users (\n" + "  userid text PRIMARY KEY,\n" + "  first_name text\n" + ");");
		session.execute("INSERT INTO users (userid, first_name) VALUES ('White', 'Walter');");

		StepVerifier.create(reactiveSession.execute("SELECT * FROM users;")).consumeNextWith(actual -> {

			StepVerifier.create(actual.rows()).consumeNextWith(row -> {

				assertThat(row.getString("userid")).isEqualTo("White");
			}).verifyComplete();
		}).verifyComplete();
	}

	@Test // DATACASS-335
	public void executeShouldPrepareStatement() throws Exception {

		session.execute("CREATE TABLE users (\n" + "  userid text PRIMARY KEY,\n" + "  first_name text\n" + ");");

		StepVerifier.create(reactiveSession.prepare("INSERT INTO users (userid, first_name) VALUES (?, ?);"))
				.consumeNextWith(actual -> {

					assertThat(actual.getQueryString()).isEqualTo("INSERT INTO users (userid, first_name) VALUES (?, ?);");
				}).verifyComplete();
	}

	private KeyspaceMetadata getKeyspaceMetadata() {
		return cluster.getMetadata().getKeyspace(this.session.getLoggedKeyspace());
	}
}
