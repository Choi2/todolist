package kr.or.connect.todo.service;

import java.util.List;

import kr.or.connect.todo.domain.Todo;

public interface TodoService {
	public boolean insertTodo(String todo); 
	public boolean deleteTodo(int id);
	public boolean updateCompleted(Todo todo); 
	public int countTodo();
	public int selectLatestId();
	public List<Todo> selectAll();
}
