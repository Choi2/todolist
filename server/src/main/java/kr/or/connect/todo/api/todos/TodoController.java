package kr.or.connect.todo.api.todos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import kr.or.connect.todo.domain.Todo;
import kr.or.connect.todo.service.TodoService;

@RestController
public class TodoController {
	
	private TodoService todoService;
	
	@Autowired
	public TodoController(TodoService todoService) {
		this.todoService = todoService;
	}

	@PostMapping(value="/insert")
	public Map<String, String> insertTodo(String todo) {
		
		Map<String, String> result = new HashMap<String, String>();
		
		if(todo == "") {
			result.put("SUCCESS", "NOTWRITE");
			return result;
		}
		
		if(todoService.insertTodo(todo)) {
			result.put("SUCCESS", "YES");
		}
		
		else {
			result.put("SUCCESS", "NO");
		}
		
		return result;
	}
	
	@PostMapping(value="/delete")
	public Map<String, String> deleteTodo(int id) {
		
		Map<String, String> result = new HashMap<String, String>();
		
		if(todoService.deleteTodo(id)) {
			result.put("id",String.valueOf(id));
			result.put("SUCCESS", "YES");
		}
		
		else {
			result.put("SUCCESS", "NO");
		}
		
		return result;
	}
	
	@PostMapping(value="/completed")
	public Map<String, String> updateCompleted(Todo todo) {
		
		
		Map<String, String> result = new HashMap<String, String>();
		
		if(todoService.updateCompleted(todo)) {
			result.put("SUCCESS", "YES");
			result.put("completed", String.valueOf(todo.getCompleted()));
		}
		
		else {
			result.put("SUCCESS", "NO");
		}
		
		return result;
	}
	
	

	@GetMapping(value="/") 
	public ModelAndView index() {
		
		ModelAndView view = new ModelAndView();
		view.setViewName("index.html");
		
		return view;
	}
	

	@PostMapping(value="/") 
	public Map<String, Object> init() {
		
		Map<String, Object> result = new HashMap<String, Object>();
		List<Todo> list = todoService.selectAll();
		int resultCount = todoService.countTodo();
		
		result.put("list", list);
		result.put("size", list.size());
		result.put("total", resultCount);
		
		return result;
	}
	

	@PostMapping(value="/counttodo") 
	public Map<String, Object> countTodo() {
		
		Map<String, Object> result = new HashMap<String, Object>();
		int resultCount = todoService.countTodo();
		result.put("total", resultCount);
		
		return result;
	}
	
	@PostMapping(value="/selectlatestid") 
	public Map<String, Object> selectLatestId() {
		
		Map<String, Object> result = new HashMap<String, Object>();
		int id = todoService.selectLatestId();
		result.put("id", id);
		return result;
	}
	
	@PostMapping(value="/deleteallcompleted") 
	public Map<String, String> deleteAllCompleted() {
		
		Map<String, String> result = new HashMap<String, String>();
		
		if(todoService.deleteAllCompleted()) {
			result.put("SUCCESS", "YES");
		}
		
		else {
			result.put("SUCCESS", "NO");
		}
		
		return result;
	}
	
	
}
