var page = 1;
$(document).ready(function() {
	$('#curDailyTasks').sortable();
	$('#curDailyTasks').disableSelection();
	$("#last").click(function() {
		page -= 1;
		if(page==0)page=1;
		loadCurDailyTasks();
	});
	$("#cur").parent().click(function() {
		loadCurDailyTasks();
	});
	$("#next").click(function() {
		page += 1;
		loadCurDailyTasks();
	});
	loadCurDailyTasks();
	$('#validTime').datetimepicker({
		timeFormat: 'HH:mm:ss',
		dateFormat: 'yy-mm-dd',
		timeInput: true,
		dateInput: true
	});
	$('#addDailyTask').click(function() {
		$('#id').val('');
		$('#template').val('post');
		$('#validTime').datetimepicker('setDate',new Date());
		$('#postId').val('');
		$('#clickUrl').val('');
		$('#editTitle').text('新增每日任务');
		$('#editDailyTask').slideDown();
	});
	$("#sortDailyTask").click(function() {
		$.ajax({
			type:'post',
			url:ctx+'/editor/dataLine/sort.v?type=dailyTask',
			data:$("#sortForm").serialize(),
			dataType:'json',
			success:function(rst){
				console.info(rst);
				if (rst.err==0) {
					alert("保存成功!");
				} else {
					alert(rst.msg);
				}
			},
			error:function(){
				alert("error");
			}
		})
	});
	$("#saveDailyTask").click(function() {
		if ((!$("#postId").val() || !$("#clickUrl").val()) && !confirm("有数据项为空，确定要保存吗？")) return;
		var cnt = {
			template:$('#template').val(),
			postId:$('#postId').val(),
			clickUrl:$('#clickUrl').val()
		};
		$("#saveDailyTask").attr('disabled','disabled');
		$.ajax({
			type:'post',
			url:ctx+'/editor/dataLine/set.v',
			data:{
				type:'dailyTask',
				id:$('#id').val(),
				cnt:JSON.stringify(cnt),
				validTime:$('#validTime').val()
			},
			dataType:'json',
			success:function(rst){
				if (rst.err==0) {
					alert("保存成功!");
					loadCurDailyTasks();
				} else {
					alert(rst.msg);
				}
				$("#saveDailyTask").removeAttr('disabled');
				$('#editDailyTask').slideUp();
			},
			error:function(){
				alert("error");
				$("#saveDailyTask").removeAttr('disabled');
			}
		});
	});
});

function loadCurDailyTasks() {
	$.ajax({
		url:ctx+'/editor/dataLine/list.v',
		data:{
			type:'dailyTask',
			page:page,
			pageSize:$('#pageSize').val()
		},
		dataType:'json',
		success:function(rst){
			if (rst.err==0) {
				$("#curDailyTasks").html($tpl(tpl_dailyTasks)(rst.dataLines));
			} else {
				alert(rst.msg);
			}
			$("#cur").text(page);
			$('#editDailyTask').slideUp();
			console.info(rst);
		},
		error:function(){
			alert("error");
		}
	})
}

function showDailyTask(id) {
	$.ajax({
		type:'post',
		url:ctx+'/editor/dataLine/get.v',
		data:{
			type:'dailyTask',
			id:id
		},
		dataType:'json',
		success:function(rst){
			if (rst.err==0) {
				var dataLine = rst.dataLine;
				$('#id').val(dataLine.id);
				$('#template').val(dataLine.cnt.template);
				$('#validTime').datetimepicker('setDate',new Date(dataLine.validTime.time));
				$('#postId').val(dataLine.cnt.postId);
				$('#clickUrl').val(dataLine.cnt.clickUrl);
				$('#editTitle').text('修改每日任务');
				$('#editDailyTask').slideDown();
			} else {
				alert(rst.msg);
			}
			console.info(rst);
		},
		error:function(){
			alert("error");
		}
	})
}

function delDailyTask(id) {
	if (!confirm("确定删除该条每日任务吗?")) return;
	$("#curDailyTaskCnt").empty();
	$.ajax({
		type:'post',
		url:ctx+'/editor/dataLine/del.v',
		data:{
			id:id
		},
		dataType:'json',
		success:function(rst){
			console.info(rst);
			if (rst.err==0) {
				alert("删除成功");
				loadCurDailyTasks();
			} else {
				alert(rst.msg);
			}
		},
		error:function(){
			alert("error");
		}
	});
}

function tpl_dailyTasks(dailyTasks) {
	if (!dailyTasks) {return;}
	var curTime = new Date().getTime();
	for (var i = 0; i < dailyTasks.length; i++) {
	/*<tr>
		<input type="hidden" name="id" value="{dailyTasks[i].id}"/>
		<input type="hidden" name="seq" value="{dailyTasks[i].seq}"/>
		<td>{ig(dailyTasks[i].cnt.postId)}</td>
		<td><a href="{ig(dailyTasks[i].cnt.clickUrl)}" target="_blank">{ig(dailyTasks[i].cnt.clickUrl)}</a></td>
		<td>{ig(dailyTasks[i].cnt.template)}</td>
		<td>{timeStamp2String(dailyTasks[i].addTime.time)}</td>
		<td class="{dailyTasks[i].validTime.time>curTime?'text-danger':'text-info'}">{timeStamp2String(dailyTasks[i].validTime.time)}</td>
		<td><a href="javascript:;" onclick="showDailyTask('{dailyTasks[i].id}')">修改</a>
			<a href="javascript:;" onclick="delDailyTask('{dailyTasks[i].id}')">删除</a></td>
	</tr>*/
	}
}

function tpl_dailyTask(dailyTask,tmpl) {
	if (!dailyTask) {alert("每日任务数据不存在!");return;}
	if (!tmpl) {alert("模板不存在!");return;}
	$("#editTitle").text("修改每日任务");
	$("#dailyTaskId").val(dailyTask.id);
	$("#tmplId").val(dailyTask.tmplId);
	$("#tmplDes").text(tmpl.des);
	$('#validTime').datetimepicker('setDate',new Date(dailyTask.validTime.time));
	var tmplCnt = tmpl.cnt;
	var dailyTaskCnt = dailyTask.cnt;
	for (var i = 0; i < tmplCnt.length; i++) {
		/*<tr>
		<td><label>{tmplCnt[i].key}</label></td>
		<td><input type="text" name="{tmplCnt[i].key}" value="{ig(dailyTaskCnt[tmplCnt[i].key])}" class="form-control" */
			if (arrContain(readOnlyTmplKey, tmplCnt[i].key)) {/*readOnly="readOnly"*/}/*></td>
		<td><pre>{tmplCnt[i].des}</pre></td>
	</tr>*/
	}
}



