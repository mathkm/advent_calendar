package com.example.service;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class AdventCalendarService {

	public List<Integer> CalendarDays() {

		YearMonth current = YearMonth.now();// 現在の日付情報を取得
		YearMonth back = current.minusMonths(1);// 先月の日付情報を取得
		// 曜日配列はString[] youbi ={月,火,水,木,金,土,日}
		int backLastDay = back.atEndOfMonth().getDayOfMonth();// 前月最終日をint型で取得(例:31)
		int lastDayWeekInt = back.atEndOfMonth().getDayOfWeek().getValue() - 1;// 前月最終日の曜日index0-6
		// ↑前月最終日 の 曜日(1から７までの数値) を-1した値を取得(自作の曜日配列の添え字(0-6)にあわせるため)

		int lastDay = current.atEndOfMonth().getDayOfMonth();// 今月の最終日をint型で取得

		List<Integer> days = new ArrayList<Integer>();
		// カレンダー１枚分のデータを入れるリスト

		if (lastDayWeekInt != 6) {// 前月最終日が日曜でない場合

			for (int i = lastDayWeekInt; i >= 0; i--) {
				days.add(backLastDay - i); // 今月のカレンダーにはみ出してくる前月分の日付を入れる
			}
			for (int i = 1; i <= lastDay; i++) {
				days.add(i);// 今月分の日付を入れる
			}
			if ((lastDay + lastDayWeekInt) < 35) {// 前月分と今月分を足しても35日に足りてないなら
				for (int i = 1; i < (35 - lastDay - lastDayWeekInt); i++) {
					days.add(i);// 35になるまで来月分の日付で空きを埋める
				}
			} else {
				for (int i = 1; i < (42 - lastDay - lastDayWeekInt); i++) {
					days.add(i);// 35を超えている時、次の７の倍数の42になるよう来月分の日付で埋める
				}
			}
		} else {// もし前月最終日が日曜日なら

			for (int i = 1; i <= lastDay; i++) {
				days.add(i);// 前月分ははみ出してこない=今月のカレンダーは１日始まりでよい
			}
			for (int i = 1; i <= (35 - lastDay); i++) {
				days.add(i);// 35になるように来月分の日付で空きを埋める
			}
		}

		// int row = days.size() / 7;// リストの要素数は35あるいは42である=カレンダーは5行または6行
		// int[][] cal = new int[row][7];// カレンダー配列はrow行7列である
		// for (int i = 0; i < cal.length; i++) {
		// for (int j = 0; j < cal[i].length; j++) {
		// cal[i][j] = days.get(j + (i * 7));// i行目のj列目に入る日付は (i*7)+j
		// // 番目の要素である
		// }
		// }
		// 表示テスト
		// for (String y : youbi) {
		// System.out.print(y);
		// }
		// System.out.println();
		// for (int i = 0; i < cal.length; i++) {
		// for (int j = 0; j < cal[i].length; j++) {
		// System.out.print(cal[i][j]);
		// }
		// System.out.println();
		// }
		return days;
	}
}