package kr.or.connect.todo.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.todo.domain.Todo;
import kr.or.connect.todo.service.TodoService;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
	
	private TodoService todoService;
	
	@Autowired
	public TodoController(TodoService todoService) {
		this.todoService = todoService;
	}

	
	@GetMapping
	public Map<String, Object> index() {
		
		Map<String, Object> result = new HashMap<String, Object>();
		List<Todo> list = todoService.selectAll();
		int resultCount = todoService.countTodo();
		
		result.put("list", list);
		result.put("size", list.size());
		result.put("total", resultCount);
		
		return result;
	}
	
	
	@PostMapping
	public Map<String, String> insertTodo(String todo) {
		
		Map<String, String> result = new HashMap<String, String>();

		if(todo == "") {
			result.put("SUCCESS", "NOTWRITE");
			return result;
		}
		
		if(todoService.insertTodo(todo)) {
			int total = todoService.countTodo();
			List<Todo> list = todoService.selectAll();
			String id = String.valueOf(list.get(0).getId());
			result.put("total", String.valueOf(total));
			result.put("id", id);
			result.put("SUCCESS", "YES");
		}
		
		else {
			result.put("SUCCESS", "NO");
		}
		
		return result;
	}
	
	

	@DeleteMapping("/{id}")
	public Map<String, String> deleteTodo(@PathVariable int id) {
		
		Map<String, String> result = new HashMap<String, String>();
	
		
		if(todoService.deleteTodo(id)) {
			int total = todoService.countTodo();
			result.put("id",String.valueOf(id));
			result.put("total", String.valueOf(total));
			result.put("SUCCESS", "YES");
		}
		
		else {
			result.put("SUCCESS", "NO");
		}
		
		return result;
	}
	
	
	
	@PutMapping
	public Map<String, String> updateCompleted(Todo todo) {
		
		
		Map<String, String> result = new HashMap<String, String>();
		
		if(todoService.updateCompleted(todo)) {
			int total = todoService.countTodo();
			result.put("SUCCESS", "YES");
			result.put("completed", String.valueOf(todo.getCompleted()));
			result.put("total", String.valueOf(total));
		}
		
		else {
			result.put("SUCCESS", "NO");
		}
		
		return result;
	}
	

	
}
