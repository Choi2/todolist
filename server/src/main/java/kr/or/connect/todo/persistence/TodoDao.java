package kr.or.connect.todo.persistence;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.todo.domain.Todo;

@Repository
public class TodoDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private  RowMapper<Todo> rowMapper = BeanPropertyRowMapper.newInstance(Todo.class);

	private TodoSqls todosqls;
	
	public TodoDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource)
				.withTableName("todo")
				.usingGeneratedKeyColumns("id");
	}
	
	public int insertTodo(String todo) {
		Map<String, String> namedParameters = new HashMap<String, String>();
		namedParameters.put("todo", todo);
		return jdbc.update(todosqls.INSERT_TODO, namedParameters);
	}
	
	public int deleteTodo(int id) {
		Map<String, Integer> namedParameters = new HashMap<String, Integer>();
		namedParameters.put("id", id);
		return jdbc.update(todosqls.DELETE_BY_ID, namedParameters);
	}

	
	public int updateCompleted(Todo todo) {
		Map<String, Integer> namedParameters = new HashMap<String, Integer>();
		namedParameters.put("completed", todo.getCompleted());
		namedParameters.put("id", todo.getId());
		return jdbc.update(todosqls.UPDATE_COMPLETED, namedParameters);
	}
	
	
	public int countTodo() {
		Map<String, String> namedParameters = Collections.EMPTY_MAP;
		return jdbc.queryForObject(todosqls.COUNT_TODO, namedParameters, Integer.class);
	}
	
	public int selectLatestId() {
		Map<String, String> namedParameters = Collections.EMPTY_MAP;
		return jdbc.queryForObject(todosqls.SELECT_LATEST_ID, namedParameters, Integer.class);
	}
	
	public List<Todo> selectAll() {
		Map<String, Object> namedParameters = Collections.EMPTY_MAP;
		return jdbc.query(todosqls.SELECT_ALL, namedParameters, rowMapper);
	}
	
	
}
