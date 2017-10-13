package com.goto456.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseBlog<M extends BaseBlog<M>> extends Model<M> implements IBean {

	public void setId(java.lang.String id) {
		set("id", id);
	}

	public java.lang.String getId() {
		return get("id");
	}

	public void setTitle(java.lang.String title) {
		set("title", title);
	}

	public java.lang.String getTitle() {
		return get("title");
	}

	public void setAuthor(java.lang.String author) {
		set("author", author);
	}

	public java.lang.String getAuthor() {
		return get("author");
	}

	public void setSummary(java.lang.String summary) {
		set("summary", summary);
	}

	public java.lang.String getSummary() {
		return get("summary");
	}

	public void setContent(java.lang.String content) {
		set("content", content);
	}

	public java.lang.String getContent() {
		return get("content");
	}

	public void setCommentNum(java.lang.Integer commentNum) {
		set("commentNum", commentNum);
	}

	public java.lang.Integer getCommentNum() {
		return get("commentNum");
	}

	public void setHeartNum(java.lang.Integer heartNum) {
		set("heartNum", heartNum);
	}

	public java.lang.Integer getHeartNum() {
		return get("heartNum");
	}

	public void setReadNum(java.lang.Integer readNum) {
		set("readNum", readNum);
	}

	public java.lang.Integer getReadNum() {
		return get("readNum");
	}

	public void setPublishTime(java.util.Date publishTime) {
		set("publishTime", publishTime);
	}

	public java.util.Date getPublishTime() {
		return get("publishTime");
	}

	public void setUrl(java.lang.String url) {
		set("url", url);
	}

	public java.lang.String getUrl() {
		return get("url");
	}

	public void setSignature(java.lang.String signature) {
		set("signature", signature);
	}

	public java.lang.String getSignature() {
		return get("signature");
	}

	public void setCategoryID(java.lang.String categoryID) {
		set("categoryID", categoryID);
	}

	public java.lang.String getCategoryID() {
		return get("categoryID");
	}

	public void setLastUpdateTime(java.util.Date lastUpdateTime) {
		set("lastUpdateTime", lastUpdateTime);
	}

	public java.util.Date getLastUpdateTime() {
		return get("lastUpdateTime");
	}

	public void setPath(java.lang.String path) {
		set("path", path);
	}

	public java.lang.String getPath() {
		return get("path");
	}

	public void setCoverURL(java.lang.String coverURL) {
		set("coverURL", coverURL);
	}

	public java.lang.String getCoverURL() {
		return get("coverURL");
	}

	public void setType(java.lang.Integer type) {
		set("type", type);
	}

	public java.lang.Integer getType() {
		return get("type");
	}

	public void setStatus(java.lang.Integer status) {
		set("status", status);
	}

	public java.lang.Integer getStatus() {
		return get("status");
	}

	public void setStatusName(java.lang.String statusName) {
		set("statusName", statusName);
	}

	public java.lang.String getStatusName() {
		return get("statusName");
	}

	public void setHtml(java.lang.String html) {
		set("html", html);
	}

	public java.lang.String getHtml() {
		return get("html");
	}

	public void setTags(java.lang.String tags) {
		set("tags", tags);
	}

	public java.lang.String getTags() {
		return get("tags");
	}

	public void setShareNum(java.lang.Integer shareNum) {
		set("shareNum", shareNum);
	}

	public java.lang.Integer getShareNum() {
		return get("shareNum");
	}

}