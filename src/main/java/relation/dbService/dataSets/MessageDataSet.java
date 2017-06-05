package main.java.relation.dbService.dataSets;


import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;

@Entity
@Table(name = "user_message")
@TypeDefs({
        @TypeDef(name = MessageDataSet.BoolType.NAME, typeClass = MessageDataSet.BoolType.class)
        })
public class MessageDataSet implements Serializable {
    private static final long serialVersionUID = -8706689714326132798L;

    @Id
    @Column(name = "message_id", unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long messageId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "receaver_msg_deleted")
    @Type(type = BoolType.NAME)
    private Boolean receaverMsgDeletedFlag;

    @Column (name = "poster_msg_deleted")
    @Type(type = BoolType.NAME)
    private Boolean posterMsgDeletedFlag;

    @Column (name = "text")
    private String text;

    @Column (name = "date")
    private Date date;

    public MessageDataSet() {
    }

    public MessageDataSet(Long userId, Boolean receaverMsgDeletedFlag, Boolean posterMsgDeletedFlag, String text, Date date) {
        this.setId(userId);
        //this.setMessageId(messageId);
        this.setReceaverMsgDeleted(receaverMsgDeletedFlag);
        this.setPosterMsgDeleted(posterMsgDeletedFlag);
        this.setText(text);
        this.setDate(date);
    }



    public Long getId() {
        return userId;
    }

    public void setId(Long userId) {
        this.userId = userId;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Boolean getReceaverMsgDeleated() {
        return receaverMsgDeletedFlag;
    }

    public void setReceaverMsgDeleted(Boolean flag) {
        this.receaverMsgDeletedFlag = flag;
    }

    public Boolean getPosterMsgDeleted() {
        return posterMsgDeletedFlag;
    }

    public void setPosterMsgDeleted(Boolean flag) {
        this.posterMsgDeletedFlag = flag;
    }

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

    public String toString() {
        return "MessageDataSet{" +
                "id=" + userId +
                ", message_id='" + messageId + '\'' +
                '}';
    }

    public static class BoolType implements UserType, ParameterizedType {

        public static final String NAME = "TF";

        private static final Logger LOGGER = Logger.getLogger(BoolType.NAME);

        private int length = 1;

        @Override
        public int[] sqlTypes() {
            return new int[] { Types.VARCHAR };
        }

        @SuppressWarnings("rawtypes")
        @Override
        public Class returnedClass() {
            return Boolean.class;
        }

        @Override
        public boolean equals(final Object x, final Object y) throws HibernateException {
            if (x == null || y == null) {
                return false;
            } else {
                return x.equals(y);
            }
        }

        @Override
        public int hashCode(final Object x) throws HibernateException {
            assert (x != null);
            return x.hashCode();
        }

        @Override
        public Object nullSafeGet(final ResultSet rs, final String[] names, final SessionImplementor session, final Object owner) throws HibernateException, SQLException {
            final String s = rs.getString(names[0]);
            if (s.isEmpty()) {
                return false;
            }
            if ("T".equalsIgnoreCase(s.trim())) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }

        @Override
        public void nullSafeSet(final PreparedStatement st, final Object value, final int index, final SessionImplementor session) throws HibernateException, SQLException {
            String s = Boolean.TRUE.equals(value) ? "T" : "F";
            if (this.length > 1) {
                s = s.trim();
            }
            st.setString(index, s);
        }

        @Override
        public Object deepCopy(final Object value) throws HibernateException {
            return value;
        }

        @Override
        public boolean isMutable() {
            return true;
        }

        @Override
        public Serializable disassemble(final Object value) throws HibernateException {
            return (Serializable) value;
        }

        @Override
        public Object assemble(final Serializable cached, final Object owner) throws HibernateException {
            return cached;
        }

        @Override
        public Object replace(final Object original, final Object target, final Object owner) throws HibernateException {
            return original;
        }

        @Override
        public void setParameterValues(final Properties parameters) {
            if (parameters != null && !parameters.isEmpty()) {
                final String lengthString = parameters.getProperty("length");
                try {
                    if (!lengthString.isEmpty()) {
                        this.length = Integer.parseInt(lengthString);
                    }
                } catch (final NumberFormatException e) {
                    LOGGER.warning("Error parsing int " + lengthString);
                }
            }
        }

    }
}