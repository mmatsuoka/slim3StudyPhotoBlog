<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>

<script type="text/javascript">
jQuery(function($) {
	  $('.show_thumb').click(function(event) {
	    // リンクの移動はキャンセル
	    event.preventDefault();

	    // 画像の情報を準備
	    var elLink = $(this);
	    var title = elLink.children().attr('alt');
	    var url = elLink.attr('href');
	    var width = 640; // Math.min(640, screen.width);
	    var height = 640; // Math.min(640, screen.height);

	    // ウィンドウを開く際のオプションを準備
	    var left = undefined;
	    var top = undefined;
	    var optionTextList = [];
	    $.each({
	      width: width,
	      height: height,
	      left: left,
	      top: top,
	      menubar: 'no',
	      toolbar: 'no',
	      location: 'no',
	      status: 'no',
	      resizable: 'no',
	      scrollbars: 'no'
	    }, function(name, value) {
	      if (value !== undefined) {
	        optionTextList.push(name + '=' + value);
	      }
	    });

	    // ウィンドウを開く
	    var thumbWindow = window.open('about:blank', null, optionTextList.join(','));
	    thumbWindow.document.open();
	    thumbWindow.document.write(

	      '<html>' +
	        '<head>' +
	          '<meta charset="utf-8" />' +
	          '<title>' + title + '</title>' +
	        '</head>' +
	        '<body style="margin:0;">' +
	          '<div' +
	            ' style="cursor: pointer;"' +
	            ' title="クリックして閉じる"' +
	            ' onclick="window.close();">' +
	            '<img src="' + url + '"' +
	              //' width="' + width + '"' +
	              //' height="' + height + '"' +
	              ' alt="" />' +
	          '</div>' +
	        '</body>' +
	      '</html>' +
	      '');
	    thumbWindow.document.close();
	  });
	});
</script>

<title>SimpleBBS</title>
<link href="/css/bbs.css" rel="stylesheet" type="text/css" />
</head>
<body>
  <div id="page">
  <h1>SimpleBBS</h1>
  <hr>
  <div class="err">${errors.message}</div>
  <form method="post" action="deleteEntry" style="display: inline" onsubmit="return confirm('この記事を削除します。よろしいですか？')">
    <input type="hidden" ${f:hidden("key")}/>
    <input type="hidden" ${f:hidden("password")}/>
    <input type="submit" value=" この記事を削除する " class="button"/>
  </form>
  <hr>
  <form method="post" action="updateEntry"  enctype="multipart/form-data" >
    <input type="hidden" ${f:hidden("key")}/>
    <input type="hidden" ${f:hidden("password")}/>
    <table>
      <thead><tr><td colspan="2">記事の修正</td></tr></thead>
      <tbody>
        <tr><td class="label">タイトル</td><td class="elem"><input type="text" ${f:text("title")} class="normal ${f:errorClass("subject","err")}"/><span class="err">${errors.subject}</span></td></tr>
        <tr><td>投稿者</td><td><input type="text" ${f:text("username")} class="normal ${f:errorClass("username","err")}"/><span class="err">${errors.username}</span></td></tr>
        <tr><td>写真</td><td>
       <a class="show_thumb" href="/bbs/image?key=${f:h(key)}">
       <img title="クリックすると原寸大で表示" src="/bbs/image?key=${f:h(key)}" style="max-height:300px; max-width:200px;" />
      	</a>
        <div class="err">${errors.photo}</div>
        <input type="file" name="photo" />
       </tr>


        <tr><td colspan=2><input type="submit" value=" 更新する " class="button"/><input type="reset" value=" リセット " class="button" ></td></tr>
      </tbody>
    </table>
  </form>
  <p><a href="javascript:history.back()">戻る</a></p>
  </div>
</body>
</html>
