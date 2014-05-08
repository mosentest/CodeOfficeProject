function clearMessages() {
	$('.errorMessage').html("");
	$('.warnMessage').html("");
	$('.noticeMessage').html("");
}

function showErrorMessage(message) {
	$(window).scrollTop(0);
	$('.errorMessage').append("<p>" + message + "</p>");
}


function showWarnMessage(message) {
	$(window).scrollTop(0);
	$('.warnMessage').append("<p>" + message + "</p>");
}


function showNoticeMessage(message) {
	$(window).scrollTop(0);
	$('.noticeMessage').append("<p>" + message + "</p>");
}

var MONTH = "month";
var MONTHS = "months";
var DAY = "day";
var DAYS = "days";
var WEEK = "week";
var WEEKS = "weeks";
var HOUR = "hour";
var HOURS = "hours";
var MINUTE = "minute";
var MINUTES = "minutes";
var RECENT = "recently";

var DIFF_M = 60000;
var MINUTE_DIFF = 1;
var HOUR_DIFF = 60;
var DAY_DIFF = 1440;
var WEEK_DIFF = 10080;
var MONTH_DIFF = 43200;

function dateFormat(date) {
	var current = new Date();
	date = new Date(date);
	var diff = (current.getTime() - date.getTime()) / DIFF_M;
	
	var year = date.getFullYear();
	var month = date.getMonth();
	if (month < 10) {
		month = "0" + month;
	}
	var day = date.getDate();
	if (day < 10) {
		day = "0" + day;
	}
	
	if (diff >= MONTH_DIFF * 4) {
		return (year - 2000) + "-" + month + "-" + day;
	} else if (diff >= MONTH_DIFF * 2) {
		return diff / MONTH_DIFF + " " + MONTHS + " ago";
	} else if (diff >= MONTH_DIFF) {
		return "1 " + MONTH + " ago";
	} else if (diff >= WEEK_DIFF * 2) {
		return diff / WEEK_DIFF + " " + WEEKS + " ago";
	} else if (diff >= WEEK_DIFF) {
		return "1 " + WEEK + " ago";
	} else if (diff >= DAY_DIFF * 2) {
		return diff / DAY_DIFF + " " + DAYS + " ago";
	} else if (diff >= DAY_DIFF) {
		return "1 " + DAY + " ago";
	} else if (diff >= HOUR_DIFF * 2) {
		return diff / HOUR_DIFF + " " + HOURS + " ago";
	} else if (diff >= HOUR_DIFF) {
		return "1 " + HOUR + " ago";
	} else if (diff >= MINUTE_DIFF * 2) {
		return diff / MINUTE_DIFF + " " + MINUTES + " ago";
	} else if (diff >= MINUTE_DIFF) {
		return "1 " + MINUTE + " ago";
	} else {
		return RECENT;
	}
}