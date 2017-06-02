package kr.or.connect.todo.persistence;

public class TodoSqls {
	public static final String DELETE_BY_ID =
			"DELETE FROM todo WHERE id= :id";
	public static final String INSERT_TODO =
			"INSERT INTO TODO (TODO, COMPLETED) VALUES (:todo, 0)";
	public static final String SELECT_ALL =
			"SELECT id, todo, completed, date FROM todo ORDER BY id DESC";
	public static final String SELECT_LATEST_ID =
			"SELECT id FROM todo ORDER BY id DESC LIMIT 1";
	public static final String COUNT_TODO =
			"SELECT COUNT(*) FROM todo WHERE completed = 0";
	public static final String UPDATE_COMPLETED =
			"UPDATE todo SET completed = :completed WHERE id= :id";
	public static final String DELECT_ALL_COMPLETED =
			"DELETE FROM todo WHERE completed = 1";
}
