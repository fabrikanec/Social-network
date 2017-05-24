package main.java.relation.messageSystem;


import main.java.relation.dbService.DBServiceInterface;

/**
 * Instruction set.
 */
public interface Executor {
    void exec(Abonent abonent) throws WrongTargetMessageException;
    void exec(DBServiceInterface accountService);
}
