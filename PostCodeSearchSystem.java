package postcodesearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class PostCodeSearchSystem {
	
	protected final BufferedReader reader = 
			new BufferedReader(new InputStreamReader(System.in));
	
	// 郵便番号検索の流れ
	public void codeSearch() {	
		init();	
		csv_road();
		do {
			if(search_choice().equals("1")) {
				input_number();
				display_number();
				search_number();
			} else {
				input_ken();
				input_city();
				display_number();
				search_address();
			}
			result_title();
			search_result();
		} while(next());
		end();
	}
	
	protected abstract void init();
	protected abstract void csv_road();
	protected abstract String search_choice();
	protected abstract String input_number();
	protected abstract String input_ken();
	protected abstract String input_city();
	protected abstract void display_number();
	protected abstract void search_number();
	protected abstract void search_address();
	protected abstract void result_title();
	protected abstract void search_result();
	protected abstract void end();
	
	private boolean next() {
		String next = null;
		try {
			while (true) {
				System.out.println("");
				System.out.print("新たな条件で検索しますか？(y/n)：");
				next = reader.readLine();
				if (next.matches("[a-zA-Z]")) {
					break;
				} else {
					System.out.println("無効な入力です。アルファベット1文字を入力してください。");
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		if (next.equalsIgnoreCase("y")) {
			return true;
        } else {
            return false;
        }
	}

}