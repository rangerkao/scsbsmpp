<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="/scsbsmpp/images/favicon.ico" rel="SHORTCUT ICON">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上傳通訊錄</title>
</head>
<body>
	<form action="uploadfile.jsp" enctype="multipart/form-data" method="post" name="upload">
		<p>上傳檔案： <input type="file" name="file" size="20" maxlength="20" /> </p>
		<p>檔案說明： <input type="text" name="filedesc" size="30" maxlength="50" /> </p>
		<p> <input type="submit"value="上傳" /> <input type="reset" value="清除" /> </p>
	</form>
	<input type="button" onclick="self.close()" value="關閉">
</body>
</html>