package postcodesearch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PostCodeSearch extends PostCodeSearchSystem {
	
	protected final BufferedReader reader = 
			new BufferedReader(new InputStreamReader(System.in));
	
	private List<PostCode> postCodes;
    private String in_number = null;
    private String in_ken = null;
    private String in_city = null;
	private String display_numbers = null;
	private List<PostCode> filteredPostCodes;
    
	public List<PostCode> getPostCodes() {
		return postCodes;
	}
	
	public String getIn_number() {
		return in_number;
	}

	public String getIn_ken() {
		return in_ken;
	}

	public String getIn_city() {
		return in_city;
	}
	
	public String getDisplay_numbers() {
		return display_numbers;
	}
	
	public List<PostCode> getFilteredPostCodes() {
		return filteredPostCodes;
	}
	
	public void separateLine() {
		System.out.println("---------------------------------------");
	}
	
	public void lineBreaks() {
		System.out.println("");
	}
	
	@Override
	protected void init() {
		System.out.println("郵便番号検索");
		separateLine();
	}
	
	//データを読み込む
    @Override
    protected void csv_road() {
    	String csvFile = "郵便番号データ.csv";
    	String line;
    	String csvSplitBy = ",";
    	
    	postCodes = new ArrayList<PostCode>();
    	
    	try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
    		while ((line = br.readLine()) != null) {
    			String[] data = line.split(csvSplitBy);
    			if (data.length == 9) {
    				PostCode postCode = new PostCode(data[0],data[1],data[2],data[3],data[4],data[5],data[6],data[7],data[8]);
    				postCodes.add(postCode);
    			} else {
    				System.out.println("不正な値です。");
    			}
    		}
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
	
	@Override
	protected String search_choice() {
		String choice = null;
		try {
			while(true) {
				System.out.print("検索条件を選択(1･･･郵便番号/2･･･住所)：");
				choice = reader.readLine();
				if (choice.matches("\\d")) {
					break;
				} else {
					System.out.println("不正な値です。入力し直してください。");
				}
			}
			separateLine();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return choice;
	}
	
	@Override
	protected String input_number() {
		try {
			while(true) {
				System.out.print("検索する郵便番号を入力(数字1～7桁)：");
				in_number = reader.readLine();
				if (in_number.matches("\\d{1,7}")) {
					break;
				} else {
					System.out.println("不正な値です。入力し直してください。");
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		return in_number;
	}
	
	@Override
	protected String input_ken() {
		try {
			System.out.print("検索する都道府県を入力(漢字)：");
			in_ken = reader.readLine();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return in_ken;
	}
	
	@Override
	protected String input_city() {
		try {
			System.out.print("検索する市区町村を入力(漢字)：");
			in_city = reader.readLine();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return in_city;
	}
	
	public boolean isNumeric(String str) {
		if (str == null || str.isEmpty()) {
			return false;
		}
		for (char c : str.toCharArray()) {
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	protected void display_number() {
		try {
			while(true) {
				System.out.print("表示件数を入力：");
				display_numbers = reader.readLine();
				if (isNumeric(display_numbers) && !(display_numbers.equals("0"))) {
					break;
				} else {
					System.out.println("不正な値です。入力し直してください。");
				}
			}
			separateLine();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void result_title() {
		System.out.println("<< 検索結果 >>");
	}
	
	@Override
	protected void search_number() {
		filteredPostCodes = new ArrayList<>();
		for (PostCode postCode : postCodes) { 
//			if (postCode.getNew_number().matches(".*" + this.in_number + ".*")) {
			if (postCode.getNew_number().contains(this.in_number)) {
				filteredPostCodes.add(postCode);
			}
		}
		Collections.sort(filteredPostCodes, Comparator.comparing(PostCode::getNew_number)
                .thenComparing(PostCode::getKana_townarea));
	}
	
	@Override
	protected void search_address() {
		filteredPostCodes = new ArrayList<>();
		for (PostCode postCode : postCodes) { 
			if ((postCode.getKanji_ken().equals(this.in_ken))&&
					(postCode.getKanji_city().equals(this.in_city))) {
				filteredPostCodes.add(postCode);
			}
		}
		Collections.sort(filteredPostCodes, Comparator.comparing(PostCode::getKana_townarea));
	}
	
	@Override
	protected void search_result() {
		int totalResults;
		totalResults = filteredPostCodes.size();
		if(filteredPostCodes.size() == 0) {
			System.out.println("該当なし");
		} else {
			int currentIndex = 0;
			try {
				while (currentIndex < totalResults) {
					int endIndex = Math.min(currentIndex + Integer.parseInt(display_numbers), totalResults);	            
					for (int i = currentIndex; i < endIndex; i++) {
						System.out.println(filteredPostCodes.get(i));
					}
					separateLine();
					System.out.println("表示件数：" + (currentIndex + 1) + "～" + endIndex + " / 検索件数：" + totalResults);
					lineBreaks();
					if (endIndex >= totalResults) {
						break;
					}
					System.out.print("表示を継続しますか？(y/n)：");
					String retry = reader.readLine();
					separateLine();
					if (retry.equalsIgnoreCase("y")) {
						currentIndex += Integer.parseInt(display_numbers);
					} else {
						break;
					}
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	protected void end() {
		separateLine();
		System.out.println("郵便番号検索終了");
	}
}
