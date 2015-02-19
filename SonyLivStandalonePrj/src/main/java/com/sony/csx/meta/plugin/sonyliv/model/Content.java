package com.sony.csx.meta.plugin.sonyliv.model;

public class Content {

	private String id;

	private String type;

	private String name;

	private String video_thumb;

	private String likes;

	private String pub_date;

	private int played;

	private String video_length;

	private String video_url;

	private String episode_number;

	private String show_id;

	private String show_title;

	private String show_url;

	private String video_social_url;

	private String brightcove_id;

	private String playlist_status;

	private String playlist_url;

	private String like_status;

	private String like_url;

	private String video_views;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVideo_thumb() {
		return video_thumb;
	}

	public void setVideo_thumb(String video_thumb) {
		this.video_thumb = video_thumb;
	}

	public String getLikes() {
		return likes;
	}

	public void setLikes(String likes) {
		this.likes = likes;
	}

	public String getPub_date() {
		return pub_date;
	}

	public void setPub_date(String pub_date) {
		this.pub_date = pub_date;
	}

	public int getPlayed() {
		return played;
	}

	public void setPlayed(int played) {
		this.played = played;
	}

	public String getVideo_length() {
		return video_length;
	}

	public void setVideo_length(String video_length) {
		this.video_length = video_length;
	}

	public String getVideo_url() {
		return video_url;
	}

	public void setVideo_url(String video_url) {
		this.video_url = video_url;
	}

	public String getEpisode_number() {
		return episode_number;
	}

	public void setEpisode_number(String episode_number) {
		this.episode_number = episode_number;
	}

	public String getShow_id() {
		return show_id;
	}

	public void setShow_id(String show_id) {
		this.show_id = show_id;
	}

	public String getShow_title() {
		return show_title;
	}

	public void setShow_title(String show_title) {
		this.show_title = show_title;
	}

	public String getShow_url() {
		return show_url;
	}

	public void setShow_url(String show_url) {
		this.show_url = show_url;
	}

	public String getVideo_social_url() {
		return video_social_url;
	}

	public void setVideo_social_url(String video_social_url) {
		this.video_social_url = video_social_url;
	}

	public String getBrightcove_id() {
		return brightcove_id;
	}

	public void setBrightcove_id(String brightcove_id) {
		this.brightcove_id = brightcove_id;
	}

	public String getPlaylist_status() {
		return playlist_status;
	}

	public void setPlaylist_status(String playlist_status) {
		this.playlist_status = playlist_status;
	}

	public String getPlaylist_url() {
		return playlist_url;
	}

	public void setPlaylist_url(String playlist_url) {
		this.playlist_url = playlist_url;
	}

	public String getLike_status() {
		return like_status;
	}

	public void setLike_status(String like_status) {
		this.like_status = like_status;
	}

	public String getLike_url() {
		return like_url;
	}

	public void setLike_url(String like_url) {
		this.like_url = like_url;
	}

	public String getVideo_views() {
		return video_views;
	}

	public void setVideo_views(String video_views) {
		this.video_views = video_views;
	}

	@Override
	public String toString() {
		return "Content [id=" + id + ", type=" + type + ", name=" + name
				+ ", video_thumb=" + video_thumb + ", likes=" + likes
				+ ", pub_date=" + pub_date + ", played=" + played
				+ ", video_length=" + video_length + ", video_url=" + video_url
				+ ", episode_number=" + episode_number + ", show_id=" + show_id
				+ ", show_title=" + show_title + ", show_url=" + show_url
				+ ", video_social_url=" + video_social_url + ", brightcove_id="
				+ brightcove_id + ", playlist_status=" + playlist_status
				+ ", playlist_url=" + playlist_url + ", like_status="
				+ like_status + ", like_url=" + like_url + ", video_views="
				+ video_views + "]";
	}

}
