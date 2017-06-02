package kr.or.connect.todo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.or.connect.todo.domain.Todo;
import kr.or.connect.todo.persistence.TodoDao;

@Service
public class TodoServiceImpl implements TodoService {

	private TodoDao todoDao;
	
	public TodoServiceImpl(TodoDao todoDao) {
		this.todoDao = todoDao;
	}
	
	
	@Override
	public boolean insertTodo(String todo) {
		return todoDao.insertTodo(todo) > 0;
	}


	@Override
	public List<Todo> selectAll() {
		return todoDao.selectAll();
	}


	@Override
	public boolean deleteTodo(int id) {
		return todoDao.deleteTodo(id) > 0;
	}


	@Override
	public boolean updateCompleted(Todo todo) {
		
		if(todo.getCompleted() > 0) {todo.setCompleted(0);}
		else {todo.setCompleted(1);}
		
		return todoDao.updateCompleted(todo) > 0;
	}


	@Override
	public int countTodo() {
		return todoDao.countTodo();
	}


	@Override
	public int selectLatestId() {
		return todoDao.selectLatestId();
	}

}
