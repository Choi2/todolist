package kr.or.connect.todo.persistence;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.todo.domain.Todo;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TodoDaoTest {
	
	@Autowired
	private TodoDao todoDao;
	
	@Test
	public void shouldCount() {
		int count = todoDao.countTodo();
	//	System.out.println(count);
	}
	
	@Test
	public void shouldSelectAll() {
		Collection<Todo> listTodo = todoDao.selectAll();
	//	System.out.println(listTodo);
	}
}
