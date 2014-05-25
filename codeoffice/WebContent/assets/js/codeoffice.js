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