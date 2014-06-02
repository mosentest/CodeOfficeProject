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
	$('.dropdown ul').mouseleave(function() {
		$(this).css({'left' : '-9999px'});
	});
	$('.image-select-indicator').click(function() {
		$('.image-select-ul-list').css({'left' : '-9999px'});
		var position = $(this).position();
		$(this).next('.image-select-ul-list').css({'left' : position.left, 'top' : position.top + 27});
		return false;
	});
	$('.image-select-ul-list').mouseleave(function() {
		$(this).css({'left' : '-9999px'});
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
	$('.color-info-choose').mouseenter(function() {
		$(this).css('background-color', LightenDarkenColor($(this).css('background-color'), 20));
	});
	$('.color-info-choose').mouseleave(function() {
		$(this).css('background-color', LightenDarkenColor($(this).css('background-color'), -20));
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
function remoteSubmit(event, url, message) {
	if (!confirm(message)) {
		event.preventDefault();
		return false;
	}
	$('#remoteForm').attr('action', url);
	$('#remoteForm').submit();
}

function LightenDarkenColor(col, amt) {
	var usePound = false;
	col = rgb2hex(col);
  
	if (col[0] == "#") {
		col = col.slice(1);
		usePound = true;
	}
 
	var num = parseInt(col,16);
 
	var r = (num >> 16) + amt;
 
	if (r > 255) r = 255;
	else if  (r < 0) r = 0;
 
	var b = ((num >> 8) & 0x00FF) + amt;
 
	if (b > 255) b = 255;
	else if  (b < 0) b = 0;
 
	var g = (num & 0x0000FF) + amt;
 
	if (g > 255) g = 255;
	else if (g < 0) g = 0;
 
	return (usePound?"#":"") + (g | (b << 8) | (r << 16)).toString(16);
}
var hexDigits = new Array("0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"); 

function rgb2hex(rgb) {
	rgb = rgb.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);
	return "#" + hex(rgb[1]) + hex(rgb[2]) + hex(rgb[3]);
}

function hex(x) {
	return isNaN(x) ? "00" : hexDigits[(x - x % 16) / 16] + hexDigits[x % 16];
}