package fun.gengzi.service;

public interface ShardingNewDataSourceSqlExecuteService<T> {

    T asyncSqlExecute(T data, String methodReference, Object args);
}
