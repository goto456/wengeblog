





layui.define(['common', 'api','form','layer','laytpl'], function(exports){
	var common = layui.common;
	var api = layui.api;
	var layer = layui.layer;
	var laytpl = layui.laytpl;
	var form = layui.form();
	
	var action = {
		loadCategory:function(){
			api.allCategory({},function(res){
				console.log(res)
				if(res.code == 0){
					laytpl($("#template").html()).render(res,function(html){
						$("#category").html(html);
						form.render();
					});
				} else {
					layer.msg(res.msg || res.code, {
						shift: 6
					});
				}
			});
		}
		
	};
	
	action.loadCategory();
	
	var publishEditor = editormd("publish-editormd", {
		height: 740,
		path: 'static/editor.md/lib/',
		toolbarIcons:function(){
			return  [
                "undo", "redo", "|",
                "bold", "del", "italic", "quote", "ucwords", "uppercase", "lowercase", "|",
                "h1", "h2", "h3", "h4", "h5", "h6", "|",
                "list-ul", "list-ol", "hr", "|",
                "link", "reference-link", "image", "code", "preformatted-text", "code-block", "table", "datetime", "emoji", "html-entities", "pagebreak", "|",
                "goto-line", "watch", "preview", "fullscreen", "clear", "search", "|",
                "help", "info"
	        ];
		},
		codeFold: true,
		saveHTMLToTextarea: true, // 保存 HTML 到 Textarea
		searchReplace: true,
		watch : false,                // 关闭实时预览
		htmlDecode: "style,script,iframe|on*", // 开启 HTML 标签解析，为了安全性，默认不开启    
		taskList: true,
		tocm: true,                  // Using [TOCM]
		tex: true,                   // 开启科学公式TeX语言支持，默认关闭
		flowChart: true,             // 开启流程图支持，默认关闭
		sequenceDiagram: true,       // 开启时序/序列图支持，默认关闭,
		imageUpload: true,
		imageFormats: ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
		imageUploadURL: api.UPLOAD_IMAGE_URL,
		onload: function() {
			/*$.get("test.md", function(data) {
				publishEditor.setMarkdown(data);
			});*/
		}
	});
	
	$("#cover-preview-cnt").hide();
	
	layui.use('upload', function(){
		layui.upload({
			url: api.UPLOAD_BLOG_COVER_URL,
			elem:"#blog-cover-file",
			success: function(res){
				console.log(res);
				if(res.code == 0){
					$("#blog-cover").val(res.data);
					$("#cover-preview-cnt").show();
					$("#cover-preview-img").attr('src',res.data);
				} else {
					common.errorTip(res);
				}
			}
		});
	});
	
	
	//监听提交
	form.on('submit(blogReport)', function(data) {
		var content = publishEditor.getMarkdown();
		var html = publishEditor.getHTML();
		var params = data.field;
		
		delete params['publish-editormd-html-code'];
		delete params['publish-editormd-markdown-doc'];
		
		params['blog.html'] = html;
		params['blog.content'] = content;
		
		if(params['blog.status'] == 0) {
			params['blog.statusName'] = '发表';
		} else {
			params['blog.statusName'] = '草稿';
		}
		
		if(params['blog.coverURL'] == null || params['blog.coverURL']== ''){
			params['blog.type'] = '0';
		} else {
			params['blog.type'] = '1';
		}
		api.addBlog(params, function(res) {
			console.log(res);
			switch(res.code) {
				case 0:
					//window.open(res.data);
					//询问框
					layer.confirm('保存成功，立即查看？', {
					  btn: ['是','否'] //按钮
					}, function(){
					  window.open(res.data);
					  location.href='blog.html';
					}, function(){
					  location.href='blog.html';
					});
					break;
				case 1002:
					layer.msg("静态化失败", {shift: 6});
					break;
				case 1003:
					layer.msg("文章已存在", {shift: 6});
					break;
				default:
					layer.msg("保存失败", {shift: 6});
					break;
			}
		});
		return false;
	});
	
	exports('blog-add',{});
});