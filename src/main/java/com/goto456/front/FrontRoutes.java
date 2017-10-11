/**
 * Copyright (c) 2015-2016, Silly Boy 胡建洪(1043244432@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.goto456.front;

import com.jfinal.config.Routes;

/**
 * 
 *
 *
 * @author JianhongHu
 * @version 1.0
 * @date 2016年10月28日
 */
public class FrontRoutes extends Routes {

	@Override
	public void config() {
		add("/",MainController.class);
		add("/blog",BlogController.class);
		add("/comment",CommentController.class);
		add("/youlian",YoulianController.class);
		add("/notice",NoticeController.class);
	}

}
