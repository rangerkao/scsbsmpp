<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html lang="zh-tw">
<head>
<meta name="Generator" content="EditPlus">
<meta name="Author" content="男丁格爾's 脫殼玩">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<meta http-equiv="Content-Style-Type" content="text/css">
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript" src="js/swfobject.js"></script>
<title>YouTube 影片播放前後顯示預覽圖片</title>
<style type="text/css">
	#abgne-block-20120704 {
		margin: 0;
		padding: 0;
		list-style: none;
	}
	#abgne-block-20120704 li {
		margin-bottom: 5px;
	}
	#abgne-block-20120704 li a img {
		border: 0;
		vertical-align: middle;
	}
</style>
<script type="text/javascript">
	$(function(){
		var prevSize = 'large',		// 設定要取得的預覽圖的尺寸, 有寬高為 480X360及寬高為 120X90 兩種
			imgWidth = 800,			// 限制圖片的寬及 YouTube 影片的寬
			imgHeight = 520,		// 限制圖片的高及 YouTube 影片的高
			flashVars = {
				enablejsapi: 1, 
				autoplay: 1,		// 是否自動播放; 1: 自動, 0:不自動
				modestbranding: 1,	// 是否隱藏在播放控制器上的 Youtube logo(不過右下角的還是會有); 1: 顯示, 0: 隱藏
				showinfo: 0,		// 是否隱藏在影片上方的標題列; 1: 顯示, 0: 隱藏
				rel: 0,				// 是否隱藏在影片上方的 Share 及 More Info功能; 1: 顯示, 0: 隱藏
				controls: 0			// 是否隱藏影片下方的控制列; 1: 顯示, 0: 隱藏
			};
		
		// 一一轉換每一個超連結中的 YouTube 影片
		$('#abgne-block-20120704 li a').each(function(){
			var $this =  $(this), 
				_url = $this.attr('href'),
				_info = $this.text(), 
				_index = $this.parent().index(), 
				_embedId = 'myytplayer_' + _index, 
				_playerId = 'ytapiplayer_' + _index, 
				player, timer, checkSpeed = 500;
			
			// 先取得 vid 後, 再利用 vid 取得預覽圖片位置
			// 最後產生預覽圖片元素
			var _vid = _url.match('[\\?&]v=([^&#]*)')[1], 
				_prevUrl = 'http://img.youtube.com/vi/' + _vid + '/' + (prevSize == 'large' ? 0 : 2) + '.jpg', 
				_prev = '<img id="' + _playerId + '" src="' + _prevUrl + '" alt="' + _info + '" title="' + _info + '" width="' + imgWidth + '" height="' + imgHeight + '" />';

			// 把目前超連結的內容轉換成預覽圖片並加入 click 事件
			$this.html(_prev).on('click', 'img', function(){
				clearInterval(timer);

				// 當點擊到預覽圖片時就轉換成 YouTube 影片
				swfobject.embedSWF('http://www.youtube.com/v/' + _vid + '?playerapiid=' + _playerId, _playerId, imgWidth, imgHeight, '8', null, flashVars, { allowScriptAccess: 'always' }, { id: _embedId });
				timer = setInterval(checkStatechange, checkSpeed);
				
				return false;
			});
			
			// 監視 YouTube 影片播放狀態
			function checkStatechange(){
				if((player = $('#' + _embedId)[0]) == undefined) return;
				try{
					var currentState = player.getPlayerState();
					// 如果已經播放完畢
					if(currentState == '0'){
						clearInterval(timer);
						$this.html(_prev);
					}
				}catch(err){}
			}
		});
	});
</script>
</head>

<body>
	<ul id="abgne-block-20120704">
		<li><a href="http://www.youtube.com/watch?v=3BFXV2Q1AB4">《復仇者聯盟》正式預告片</a></li>
	</ul>
</body>
</body>
</html>