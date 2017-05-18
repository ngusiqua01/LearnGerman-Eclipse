package com.ufo.learngerman.adapter;

public class PhraseItem {

	public int id;

	public String txtKorean;
//	public String txtPinyin;
	public String txtVietnamese;

	public int catId;
	public String voice;
	public int favorite;

	public String txtEnglish;

	public String txtChinese;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTxtKorean() {
		return txtKorean;
	}

	public void setTxtKorean(String txtKorean) {
		this.txtKorean = txtKorean;
	}

//	public String getTxtPinpyn() {
//		return txtPinyin;
//	}
//
//	public void setTxtPinpyn(String txtPinpyn) {
//		this.txtPinyin = txtPinpyn;
//	}

	public String getTxtVietnamese() {
		return txtVietnamese;
	}

	public void setTxtVietnamese(String txtVietnamese) {
		this.txtVietnamese = txtVietnamese;
	}

	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	public String getVoice() {
		return voice;
	}

	public void setVoice(String voice) {
		this.voice = voice;
	}

	public int getFavorite() {
		return favorite;
	}

	public void setFavorite(int favorite) {
		this.favorite = favorite;
	}

}
