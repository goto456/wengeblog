/**
 * 
 * 
 * 青菜萝卜 评论插件
 * 
 * 
 */
layui.define(['api', 'laytpl','laypage','layer','qingface', 'form','qingeditor'], function(exports) {
	var $ = layui.jquery;
	var layer = layui.layer;
	var api = layui.api;
	var form = layui.form();
	var laypage = layui.laypage;
	var qingeditor = layui.qingeditor;
	
	var laytpl = layui.laytpl;
	
	var cas = function(content){
		return "";
	}
	
	var qingface = layui.qingface;
	
	var commentTpl = {
		commentCntrTpl:
		'<div>\
		<div class="qing-comment-head">\
				<div class="qing-comment-title">\
					<h2>{{d.title}}<span>(<span id="qing-comment-num"></span>)</span></h2>\
				</div>\
				{{#  if(d.report){ }}\
					<span class="qing-comment-report">{{d.reportTip}}</span>\
				{{# } }}\
			</div>\
			<div class="qing-comment-container" id="qing-comment-list"></div>\
			<div id="comment-pager" class="qing-text-center"></div>\
		</div>\
		{{#  if(d.report){ }}\
  			<div class="qing-comment-post">发表评论</div>\
	      	<div class="layui-form-pane qing-margin-top qing-margin-bottom">\
		        <form class="layui-form" action="post">\
		        	<div class="layui-form-item">\
					    <div class="layui-input-inline">\
					      <input type="text" name="nickname" lay-verify="required" placeholder="昵称" autocomplete="off" class="layui-input">\
					    </div>\
					    <div class="layui-input-inline">\
					      <input type="text" name="email" lay-verify="email" placeholder="邮箱" autocomplete="off" class="layui-input">\
					    </div>\
					</div>\
		          <div class="layui-form-item layui-form-text">\
		            <div class="layui-input-block">\
		              <textarea id="qing-content" name="content" required lay-verify="required" placeholder="我要发表评论"  class="layui-textarea qing-editor" style="height: 150px;"></textarea>\
		            </div>\
		          </div>\
		          <div class="layui-form-item">\
		            <input type="hidden" name="qingID" value="{{d.blogID}}">\
		            <input type="hidden" name="parent" id="qingCmtParent" value="">\
		            <button class="layui-btn" lay-filter="comment" lay-submit>提交评论</button>\
		          </div>\
		        </form>\
	      	</div>\
      	{{# } }}'
		,
		commentListTpl:
		'{{# if(d.list.length > 0) { }}\
			{{#  layui.each(d.list, function(index, item){ }}\
				<div class="qing-comment-main" data="{{item.id}}">\
					<div class="qing-comment-avatar">\
						<div class="qing-comment-img">\
							<img class="layui-circle" src="static/head/{{item.headURL}}" />\
						</div>\
					</div>\
					<div class="qing-comment-body">\
						<div class="qing-comment-meta">\
							<span class="qing-comment-author"><strong>{{item.nickname}}</strong></span>  <span>{{item.cdate}}</span>\
						</div>\
						<p class="qing-comment-content">{{item.content}}</p>\
						<div class="qing-comment-hint">\
							<span type="like"><i class="am-icon-thumbs-o-up"></i> <span>赞</span>(<em>{{item.likeNum}}</em>)</span>\
							<span type="hate"><i class="am-icon-thumbs-o-down"></i> <span>踩</span>(<em>{{item.hateNum}}</em>)</span>\
							{{# if(d.reply){ }}\
								<span type="reply"><i class="am-icon-reply"></i> <span>回复</span>(<em>{{item.replyNum}}</em>)</span>\
							{{# } }}\
						</div>\
					</div>\
				</div>\
			{{# }); }}\
		{{# } else { }}\
			<div class="qing-comment-no qing-margin-bottom">\
				<div class="qing-comment-tip">\
					{{#  if(d.report){ }}\
						暂无{{d.type}},<span class="qing-comment-shafa">快抢沙发</span>\
					{{# } else { }}\
						暂无{{d.type}}\
					{{# } }}\
				</div>\
			</div>\
		{{# } }}'
	};
	
	var plugin = {
		/**
		 * 评论组件初始化
		 * 
		 * @param {Object} options
		 * 
		 * options = {
		 * 	 elem : elem,
		 *   params: {
		 * 	
		 * 	 },
		 *   type:留言|评论|赞助
		 *   title:'热忱评论',
		 *   reportTip : '发表评论'
		 *   reply:true,
		 *   report:true,
		 *   blogID:
		 * }
		 */
		comment:function(options) {
			// validate params
			var elem = options.elem || $("#commentContainer");
			
			var params = options.params;
			
			options.title = options.title || '热忱评论';
			options.reportTip = options.reportTip || '发表评论';
			if(options.hasOwnProperty('reply')){
				options.reply = options.reply ? true : false;
			} else {
				options.reply = true;
			}
			if(options.hasOwnProperty('report')){
				options.report = options.report ? true : false;
			} else {
				options.report = true;
			}
			if(!options.hasOwnProperty('type')){
				options.type = '评论';
			} 
			// 加载评论容器
			var cntrTpl = laytpl(commentTpl.commentCntrTpl);
			cntrTpl.render(options,function(html){
				options.elem.html(html);
			});
			if(options.report){
				//加载编辑器
				qingeditor.layEditor({
					elem: '.qing-editor'
				});
			};
			
			// 注册事件
			$('.qing-comment-report').on("click",function(event){
				$('#qing-content').focus();
				$('#qingCmtParent').val("");
			});
			
			// 加载评论数据
			api.qingComment(params,function(result){
				// 加载模板
				var tpl = laytpl(commentTpl.commentListTpl);
				var data = result.data;
				data['reply'] = options.reply;
				data['report'] = options.report;
				data['type'] = options.type;
				// 渲染数据
				tpl.render(data,function(html){
					// 显示内容
					$("#qing-comment-num").html(data.totalRow);
					$("#qing-comment-list").html(html);
					// 监听事件
					plugin.onClick();
				});
				// 调用分页
				laypage({
					cont: 'comment-pager',
					pages: data.totalPage, //得到总页数
					jump: function(conf,first) {
						if(first){
							return;
						}
						plugin.pageCallback(conf.curr,params);
					}
				});
			});
		},
		
		pageCallback : function(pageNum,params){
			var np = {};
			for(var key in params) {
				np[key] = params[key];
			}
			np.pageNum = pageNum;
			api.qingComment(np,function(result){
				// 加载模板
				var tpl = laytpl(commentTpl.commentListTpl);
				// 渲染数据
				tpl.render(result.data,function(html){
					// 显示内容
					$("#qing-comment-num").html(result.data.totalRow);
					$("#qing-comment-list").html(html);
					// 监听事件
					plugin.onClick();
					// 对评论内容进行转义
					plugin.zhuanyi();
				});
				
				// 回到顶部
				var speed=200;//滑动的速度
				var top = $("#qing-comment-list").offset().top;
                $('body').animate({ scrollTop:  top - 200 }, speed);
			});
		},
		
		hate: function(elem) { //踩
			var othis = $(this);
			var id = elem.attr('data');
			var ok = othis.hasClass('qing-comment-ok');
			api.commentHate({
				ok: ok,
				id: id
			}, function(res) {
					var zans = othis.find('em').html() | 0;
					othis[ok ? 'removeClass' : 'addClass']('qing-comment-ok');
					othis.find('span').html(ok ? '踩' : '取消踩');
					othis.find('em').html(ok ? (--zans) : (++zans));
			});
		},
		
		
		like: function(elem) { //赞
			var othis = $(this);
			var id = elem.attr('data');
			var ok = othis.hasClass('qing-comment-ok');
			api.commentLike({
				ok: ok,
				id: id
			}, function(res) {
					var zans = othis.find('em').html() | 0;
					othis[ok ? 'removeClass' : 'addClass']('qing-comment-ok');
					othis.find('span').html(ok ? '赞' : '取消赞');
					othis.find('em').html(ok ? (--zans) : (++zans));
			});
		},
		
		
		reply: function(elem) { //回复
			var content = $('#qing-content');
			var val = content.val();
			content.focus();
			var aite = '@' + elem.find('.qing-comment-author').text().replace(/\s/g, '');
			$('#qingCmtParent').val(elem.attr('data'));
			if(val.indexOf(aite) !== -1){
				return;
			}
			content.val(aite + ' ' + val);
		},
		
		onClick : function(){
			$('.qing-comment-hint>span').on('click', function() {
				var othis = $(this);
				var type = othis.attr('type');
				plugin[type].call(this, othis.parents('div.qing-comment-main'));
			});
			$('.qing-comment-shafa').on("click",function(event){
				$('#qing-content').focus();
			});
		},
	}
	exports('qingcmt', plugin);
});