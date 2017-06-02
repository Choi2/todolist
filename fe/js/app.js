(function (window) {
	'use strict';

	$().ready(function(){

					$.post("/", {}, function(response) {

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
								$.post("/insert", {
											todo : $(".new-todo").val()
											}, function(response) {

												if(response.SUCCESS == "YES") {
													var todo = $(".new-todo").val();
													var addContext = addContextFunction(selectLatestId(), 0, todo);
													$(".todo-list").prepend(addContext);
													alert("글쓰기가 성공했습니다");
													$(".todo-count").children().html(countTodo());
													$('.new-todo').val('');
												}
												else if(response.SUCCESS =="NOTWRITE") {
													alert("내용이 존재하지 않으니 글을 써주세요.");
												}

												});
									}
				});


				$(document).on('click','.destroy', function() {

						$.post("/delete", {
								id : $(this).siblings('.id').val()
						}, function(response) {
								if(response.SUCCESS =="YES") {
									var id = response.id;
									$('.todo-list').children('li').each(function() {
												if($(this).children().children('.id').val() == id) {
													$(this).remove();
												}
									});

									alert("글삭제가 완료되었습니다");
								}
						});

				});


				$(document).on('click','.toggle', function() {


						var self = $(this).parent().parent('li');

						$.post("/completed", {
								id : $(this).siblings('.id').val(),
								completed : $(this).parent().parent('li').val()
						}, function(response) {
								if(response.SUCCESS =="YES") {

										if(response.completed == "0") {
												$(self).attr('class', '');
												$(self).attr('value', '0');
												var count = countTodo();
												$(".todo-count").children().html(countTodo());
										}

										else {
												$(self).attr('class', 'completed');
												$(self).attr('value', '1');
												$(".todo-count").children().html(countTodo());
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
					$.post("/deleteallcompleted", {
					}, function(response) {
							if(response.SUCCESS =="YES") {
								location.reload();
							}
					});
				});

				function countTodo() {
					var count = 11;
							$.ajax({
									url: "/counttodo",
									type: "POST",
									async: false, // ture: 비동기, false: 동기
									dataType: "json",
									success: function(data){
											count = data.total;
										}
							});
					return count;
				}

				function selectLatestId() {
					var id = 11;
							$.ajax({
									url: "/selectlatestid",
									type: "POST",
									async: false, // ture: 비동기, false: 동기
									dataType: "json",
									success: function(data){
											id = data.id;
										}
							});
					return id;
				}

			function addContextFunction(id, completed, todo) {

				var addContext = '<li value='+ completed + ' class=';
				addContext += ((completed) == '1') ? 'completed>' : '>' 	;

				addContext += '<div class="view">';
				addContext +='<input class="toggle" type="checkbox"';
				addContext += ((completed) == '1') ? 'checked>' : '>' 	;
				addContext +='<label> '+ todo +'</label>';
				addContext +='<button class="destroy"></button>';
				addContext +='<input class="edit" value="Create a TodoMVC template">';
				addContext +='<input type="hidden" class="id" value='+ id +'>';
				addContext += '</div></li>';

				return addContext;
			}

	});


})(window);
