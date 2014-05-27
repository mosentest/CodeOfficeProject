$(document).ready(function() {
	$('#header .link').mouseover(function() {
		$(this).addClass('current');
	});
	$('#header .link').mouseout(function() {
		$(this).removeClass('current');
	});
	if ($('#leftmenu').length > 0) {
		var left = $('#leftmenu').first();
		var main = $('#maincontent').first();
		if (left.height() >= main.height()) {
			left.addClass('leftmenu-border');
			main.addClass('noborder');
		} else {
			main.addClass('maincontent-border');
			left.addClass('noborder');
		}	
		
	}
	$('.dropdown-indicator').click(function() {
		$('.dropdown ul').css({'left' : '-9999px'});
		var position = $(this).position();
		$(this).next('.dropdown').find('ul').css({'left' : position.left - 10, 'top' : position.top + 27});
		return false;
	});
	$('.toggleable').click(function() {
		var input = $(this).next('input');
		var checked = input.attr('checked');
		if (checked === undefined) {
			$(this).parent().removeClass('toggle-off');
			$(this).parent().addClass('toggle-on');
			$(this).text('Enabled');
			input.attr('checked', true);
		} else {
			$(this).parent().removeClass('toggle-on');
			$(this).parent().addClass('toggle-off');
			$(this).text('Disabled');
			input.attr('checked', false);
		}
	});
	$('.dropdown ul').mouseleave(function() {
		$(this).css({'left' : '-9999px'});
	});
});

function url(url) {
	window.location = url;
}
function gotoPage(element, pageIndex) {
	console.log($(element).html());
	$(element).closest('form').find("input[name='pageIndex']").val(pageIndex);
	$(element).closest('form').submit();
}
function postDelete(url) {
	if (confirm('delete?')) {
		$.post(url, function(data, status) {
			location.reload();
		}); 
	} else {
		return false;
	}
}
function del(url) {
	if (confirm('delete?')) {
		url(url);
	} else {
		return false;
	}
}
function submitForm(element) {
	var form = $(element).closest('form');
	form.find('input').each(function() {
		if ($(this).val() == '') {
			$(this).remove();
		}
	});
	form.find('select').each(function() {
		if ($(this).val() == '') {
			$(this).remove();
		}
	});
}
function deleteForm(url) {
	if (confirm('delete?')) {
		$('#deleteForm').attr('action', url);
		$('#deleteForm').submit();
	} else {
		return false;
	}
}
function confirmSubmit(event, message) {
	if (!confirm(message)) {
		event.preventDefault();
		return false;
	}
}