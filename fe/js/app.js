(function (window) {
	'use strict';

	$().ready(function(){

					$.get("/api/todos", {}, function(response) {

							var count = response.total;

							for(var i = 0; i < response.size; i++) {

								var id = response.list[i].id;
								var completed = response.list[i].completed;
								var todo = 	response.list[i].todo;
								var addContext = addContextFunction(id, completed, todo);

								$(".todo-list").append(addContext);
						}


						$(".todo-count").children().html(count);
					});



				$('.new-todo').keydown(function(e) {
						if (e.which == 13) {
								$.post("/api/todos", {
											todo : $(".new-todo").val()
											}, function(response) {

												if(response.SUCCESS == "YES") {
													var todo = $(".new-todo").val();
													var addContext = addContextFunction(response.id, 0, todo);
													$(".todo-list").prepend(addContext);
													$(".todo-count").children().html(response.total);
													alert("글쓰기가 성공했습니다");
													$('.new-todo').val('');
												}
												else if(response.SUCCESS =="NOTWRITE") {
													alert("내용이 존재하지 않으니 글을 써주세요.");
												}

												});
									}
				});


				$(document).on('click','.destroy', function() {
						var id = $(this).parents().siblings('.id').val();
						deleteTodo(id);
						alert("글삭제가 완료되었습니다");
				});


				$(document).on('click','.toggle', function() {

						var self = $(this).parents('li');

						$.put("/api/todos", {
								id : $(this).parents('li').children('.id').val(),
								completed : $(this).parents('li').val()
						}, function(response) {
								if(response.SUCCESS =="YES") {

										if(response.completed == "0") {
												$(self).attr('class', '');
												$(self).attr('value', '0');
												$(".todo-count").children().html(response.total);
										}

										else {
												$(self).attr('class', 'completed');
												$(self).attr('value', '1');
												$(".todo-count").children().html(response.total);
										}
								}
						});

				});



				$(".filters").children().children("a").click(function(e) {
							var site = $(this).attr('href').substring(2);
							var self = this;

							if(site == 'active') {

									$('.todo-list').children('li').each(function() {

											if($(this).val() != '0') {
													$(this).attr('style','display:none');
											}

											else {
												$(this).attr('style','display:block');
											}
									});

							}

							else if(site == "completed") {
								$('.todo-list').children('li').each(function() {

										if($(this).val() != '1') {
												$(this).attr('style','display:none');
										}

										else {
											$(this).attr('style','display:block');
										}
								});
							}

							else if(site =='') {
								$('.todo-list').children('li').each(function() {
											$(this).attr('style','display:block');
								});
							}

						e.preventDefault();
						$(self).attr('class','selected');
						$(self).parent('li').siblings('li').children('a').attr('class','');
				});


				$('.clear-completed').click(function() {
						$(".todo-list").find('li').each(function() {
								if($(this).val() == '1') {
										var id = $(this).children('.id').val();
										deleteTodo(id);
								}
						});
				});

			function addContextFunction(id, completed, todo) {

				var addContext = '<li value='+ completed + ' class=';
				addContext += ((completed) == '1') ? 'completed>' : '>' 	;
				addContext +='<input type="hidden" class="id" value='+ id +'>';
				addContext += '<div class="view">';
				addContext +='<input class="toggle" type="checkbox"';
				addContext += ((completed) == '1') ? 'checked>' : '>' 	;
				addContext +='<label> '+ todo +'</label>';
				addContext +='<button class="destroy"></button>';
				addContext +='<input class="edit" value="Create a TodoMVC template">';
				addContext += '</div></li>';

				return addContext;
			}

			function deleteTodo(num) {

				$.delete("/api/todos/" + num, {
						id : num
				}, function(response) {
						if(response.SUCCESS =="YES") {
							var id = response.id;
							$('.todo-list').children('li').each(function() {
										if($(this).children('.id').val() == id) {
											$(this).remove();
										}
							});

							$(".todo-count").children().html(response.total);
						}
				});


			}

	});

	$.put = function(url, data, callback, type){

  if ( $.isFunction(data) ){
    type = type || callback,
    callback = data,
    data = {}
  }

  return $.ajax({
    url: url,
    type: 'PUT',
    success: callback,
    data: data,
    contentType: type
  });
}

$.delete = function(url, data, callback, type){

  if ( $.isFunction(data) ){
    type = type || callback,
        callback = data,
        data = {}
  }

  return $.ajax({
    url: url,
    type: 'DELETE',
    success: callback,
    data: data,
    contentType: type
  });
}


})(window);
