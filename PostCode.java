package postcodesearch;

public class PostCode {

	private String code;				//①全国地方公共団体コード　………　半角数字
	private String old_number;			//②（旧）郵便番号（5桁）　………　半角数字
	private String new_number;			//③郵便番号（7桁）　………　半角数字
	private String kana_ken;			//④住所カナ１　…………　都道府県名（全角カタカナ）
	private String kana_city;			//⑤住所カナ２　…………　市区町村名(全角カタカナ)
	private String kana_townarea;		//⑥住所カナ３　…………　町域名（全角カタカナ）
	private String kanji_ken;			//⑦住所漢字１　…………　都道府県名（漢字）
	private String kanji_city;			//⑧住所漢字２　…………　市区町村名（漢字）
	private String kanji_townarea;		//⑨住所漢字３　…………　町域名（漢字）
	
	public PostCode(String code,String old_number,String new_number,
			String kana_ken,String kana_city,String kana_townarea,
			String kanji_ken, String kanji_city, String kanji_townarea) {
		this.code = code;
		this.old_number = old_number;
		this.new_number = new_number;
		this.kana_ken = kana_ken;
		this.kana_city = kana_city;
		this.kana_townarea = kana_townarea;
		this.kanji_ken = kanji_ken;
		this.kanji_city = kanji_city;
		this.kanji_townarea = kanji_townarea;		
	}
	
	public String getCode() {
		return code;
	}
	
	public String getOld_number() {
		return old_number;
	}
	
	public String getNew_number() {
		return new_number;
	}
	
	public String getKana_ken() {
		return kana_ken;
	}
	
	public String getKana_city() {
		return kana_city;
	}
	
	public String getKana_townarea() {
		return kana_townarea;
	}
	
	public String getKanji_ken() {
		return kanji_ken;
	}
	
	public String getKanji_city() {
		return kanji_city;
	}
	
	public String getKanji_townarea() {
		return kanji_townarea;
	}
	
	@Override
    public String toString() {
        return String.format("  %s:%s%s%s",new_number,kanji_ken,kanji_city,kanji_townarea);
    }

}
