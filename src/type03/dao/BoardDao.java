package type03.dao;

import type03.dto.BoardDto;
import type03.util.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardDao {

	private PreparedStatement setPreparedStatement(PreparedStatement pstmt, Object status) throws SQLException {
		if (status instanceof String) {
			pstmt.setString(1, (String) status);
		} else {
			pstmt.setInt(1, (int) status);
		}
		return pstmt;
	}

	private ResultSet setResultSet(PreparedStatement pstmt, Object status) throws SQLException {
		if (status instanceof String) {
			pstmt.setString(1, (String) status);
		} else {
			pstmt.setInt(1, (int) status);
		}
		return pstmt.executeQuery();
	}

	public BoardDto createBoard(ResultSet rs) throws SQLException {
		BoardDto board = new BoardDto();
		board.setNum(rs.getInt("num"));
		board.setId(rs.getString("id"));
		board.setTitle(rs.getString("title"));
		board.setContent(rs.getString("content"));
		board.setWritedate(rs.getString("writedate"));
		board.setReadcount(rs.getInt("readcount"));
		return board;
	}

	// select_paging
	public List<BoardDto> select_paging(int pageNum) {
		List<BoardDto> list = new ArrayList<>();
		String sql = "select * from BOARD order by NUM desc limit ?, 3";
		try (
				Connection conn = DBManager.getConnection("oracle");
				ResultSet rs = setResultSet(conn.prepareStatement(sql), pageNum)
		) {
			while (rs.next()) {
				list.add(createBoard(rs));
			}
		} catch (SQLException e) {
			System.err.println("Exception: BoardDao Function(select_paging) Something Problem!!");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	// hotBoard
	public List<BoardDto> hotBoard() {
		List<BoardDto> list = new ArrayList<>();
		String sql = "SELECT num, title, readcount FROM board ORDER BY readcount DESC limit 3";
		try (
				Connection conn = DBManager.getConnection("oracle");
				ResultSet rs = conn.prepareStatement(sql).executeQuery()
		) {
			while (rs.next()) {
				list.add(createBoard(rs));
			}
		} catch (SQLException e) {
			System.err.println("Exception: BoardDao Function(select_paging) Something Problem!!");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return list;
	}
}
