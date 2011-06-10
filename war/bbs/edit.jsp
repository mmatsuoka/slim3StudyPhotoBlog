<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
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
        <img src="/bbs/image?key=${f:h(key)}" style="max-height:300px; max-width:200px;" />
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
