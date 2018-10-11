package type03.action;

import type03.controller.board.*;

public class ActionFactory {

	private ActionFactory() {
	}

	private static class LazyHolder {
		static final ActionFactory INSTANCE = new ActionFactory();
	}

	public static ActionFactory getInstance() {
		return LazyHolder.INSTANCE;
	}

	public Action getAction(String cmd) {
		switch (cmd) {
			case "board_list":
				return new BoardListAction();

			case "board_write":
				return new BoardWriteAction();

//			case "member_join":
//				return new MemberJoinAction();
//
//			case "member_login":
//				return new MemberLoginAction();
//
//			case "member_update":
//				return new MemberUpdateAction();
//
//			case "member_logout":
//				return new MemberLogoutAction();
//
//			case "member_updateProc":
//				return new MemberUpdateProcAction();
//
			case "board_view":
				return new BoardViewAction();
//
//			case "board_delete":
//				return new BoardDeleteAction();

			case "board_update":
				return new BoardUpdateAction();

			case "board_updateProc":
				return new BoardUpdateProcAction();
//
//			case "board_ajax":
//				return new BoardAjaxAction();
//
//			case "board_search":
//				return new BoardSearchAction();
//
//			case "reboard_reply":
//				return new ReBoardReplyAction();
//
//			case "reboard_delete":
//				return new ReBoardDeleteAction();
//
//			case "admin_sms":
//				return new SMSSendAction();

			default:
				return new BoardListAction();
		}
	}

}
