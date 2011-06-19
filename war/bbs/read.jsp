<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
  <div class="err">${errors.message}</div>
  <table>
  <thead><tr><td><div class="read_subject">${f:h(head.title)}</div></td></tr></thead>
  <tbody>
  <tr>
    <td>
      <div class="read_header">${f:h(head.username)} さん （<fmt:formatDate value="${head.postDate}" pattern="yyyy/MM/dd HH:mm:ss" />）</div>
      <hr>
      <div class="read_body">${f:br(f:h(body.text))}</div>
       <div class="read_body">
       <a class="show_thumb" href="/bbs/image?key=${f:h(head.key)}">
       <img title="クリックすると原寸大で表示" src="/bbs/image?size=s&key=${f:h(head.key)}" style="max-height:300px; max-width:200px;" /></div>
      	</a>
      <div class="read_footer">
        <span class="err">${errors.password}</span>
        <form method="post" action="edit" style="display: inline">
        <input type="hidden" name="key" value="${f:h(head.key)}"/>
        <input type="password" ${f:text("password")}" class="password ${f:errorClass("password","err")}"/>
        <input type="submit" value=" 修正 " class="button"/>
        </form>
      </div>
    </td>
  </tr>
  </tbody>
  </table>
  <hr class="separate_entry">
  <c:forEach var="c" items="${commentList}" varStatus="cs" >
    <table>
      <tbody>
        <tr>
          <td>
            <div class="read_header">No.${f:h(c.key.id)} : ${f:h(c.username)} さん （<fmt:formatDate value="${c.postDate}" pattern="yyyy/MM/dd HH:mm:ss" />）</div>
            <hr>
            <div class="read_body">${f:br(f:h(c.comment))}</div>
          </td>
        </tr>
      </tbody>
    </table>
    <div>&nbsp;</div>
  </c:forEach>
  <div class="err">${errors.post}</div>
  <form method="post" action="postComment" style="display: inline">
    <input type="hidden" name="key" value="${f:h(head.key)}"/>
    <table>
      <thead><tr><td colspan="2">コメントの投稿</td></tr></thead>
      <tbody>
        <tr><td class="label">投稿者</td><td class="elem"><input type="text" ${f:text("username")} class="normal ${f:errorClass("username","err")}"/><span class="err">${errors.username}</span></td></tr>
        <tr><td>コメント</td><td><textarea name="comment" class="smalltext ${f:errorClass("comment","err")}">${f:h(comment)}</textarea><div class="err">${errors.comment}</div></td></tr>
        <tr><td colspan=2><input type="submit" value=" 投稿する " class="button"/></td></tr>
      </tbody>
    </table>
  </form>
  <p><a href="index">戻る</a></p>
  </div>
</body>
</html>