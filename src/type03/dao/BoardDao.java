package type03.dao;

import type03.dto.BoardDto;
import type03.util.DBManager;
import type03.util.MyUtil;

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

	private BoardDto createBoard(ResultSet rs) throws SQLException {
		BoardDto board = new BoardDto();
		board.setNum(rs.getInt("num"));
		board.setId(rs.getString("id"));
		board.setTitle(rs.getString("title"));
		board.setContent(rs.getString("content"));
		board.setWritedate(rs.getString("writedate"));
		board.setReadcount(rs.getInt("readcount"));
		return board;
	}

	public List<BoardDto> getAllBoard() {
		List<BoardDto> list = new ArrayList<>();
		String sql = "select * from board order by num desc";
		try (
				Connection conn = DBManager.getConnection("mysql");
				ResultSet rs = conn.prepareStatement(sql).executeQuery()
		) {
			while (rs.next()) {
				list.add(createBoard(rs));
			}
		} catch (SQLException e) {
			MyUtil.showException(getClass().getName(), "search", e);
		}
		return list;
	}

	public List<BoardDto> getPageBoard(int pageNum) {
		List<BoardDto> list = new ArrayList<>();
		String sql = "select * from board order by num asc limit ?, 3";
		try (
				Connection conn = DBManager.getConnection("mysql");
				ResultSet rs = setResultSet(conn.prepareStatement(sql), pageNum)
		) {
			while (rs.next()) {
				list.add(createBoard(rs));
			}
		} catch (SQLException e) {
			MyUtil.showException(getClass().getName(), "getPageBoard", e);
		}
		return list;
	}

	public List<BoardDto> getHotBoard() {
		List<BoardDto> list = new ArrayList<>();
		String sql = "select * from board order by readcount desc limit 3";
		try (
				Connection conn = DBManager.getConnection("mysql");
				ResultSet rs = conn.prepareStatement(sql).executeQuery()
		) {
			while (rs.next()) {
				list.add(createBoard(rs));
			}
		} catch (SQLException e) {
			MyUtil.showException(getClass().getName(), "getHotBoard", e);
		}
		return list;
	}

	public int insertBoard(BoardDto board) {
		String sql = "insert into board (title, content, writedate, id) value (?, ?, now(), ?)";
		try (
				Connection conn = DBManager.getConnection("mysql");
				PreparedStatement pstmt = conn.prepareStatement(sql)
		) {
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getContent());
			pstmt.setString(3, board.getId());
			pstmt.executeUpdate();
			return 1;
		} catch (SQLException e) {
			MyUtil.showException(getClass().getName(), "insertBoard", e);
		}
		return -1;
	}

	public int updateBoard(BoardDto board) {
		String sql = "update board set title = ?, content = ? where num = ?";
		try (
				Connection conn = DBManager.getConnection("mysql");
				PreparedStatement pstmt = conn.prepareStatement(sql)
		) {
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getContent());
			pstmt.setInt(3, board.getNum());
			pstmt.executeUpdate();
			return 1;
		} catch (SQLException e) {
			MyUtil.showException(getClass().getName(), "updateBoard", e);
		}
		return -1;
	}

	public int deleteBoard(int num) {
		String sql = "delete from board where num = ?";
		try (
				Connection conn = DBManager.getConnection("mysql");
				PreparedStatement pstmt = conn.prepareStatement(sql)
		) {
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
			return 1;
		} catch (SQLException e) {
			MyUtil.showException(getClass().getName(), "updateBoard", e);
		}
		return -1;
	}

	public BoardDto getBoard(int num) {
		BoardDto board = new BoardDto();
		String sql = "select * from board where num = ?";
		try (
				Connection conn = DBManager.getConnection("mysql");
				ResultSet rs = setResultSet(conn.prepareStatement(sql), num)) {
			if (rs.next()) {
				board = createBoard(rs);
			}
		} catch (SQLException e) {
			MyUtil.showException(getClass().getName(), "getBoard", e);
		}
		return board;
	}

	public int readCount(int num) {
		String sql = "update board set readcount = readcount + 1 where num = ?";
		try (
				Connection conn = DBManager.getConnection("mysql");
				PreparedStatement pstmt = setPreparedStatement(conn.prepareStatement(sql), num)
		) {
			pstmt.executeUpdate();
			return 1;
		} catch (SQLException e) {
			MyUtil.showException(getClass().getName(), "readCount", e);
		}
		return -1;
	}

	public int isNextPage(int pageNum) {
		String sql = "select * from board order by num desc limit ?, 3";
		try (
				Connection conn = DBManager.getConnection("mysql");
				ResultSet rs = setResultSet(conn.prepareStatement(sql), pageNum)
		) {
			if (rs.next()) {
				return 1;
			}
		} catch (SQLException e) {
			MyUtil.showException(getClass().getName(), "isNextPage", e);
		}
		return -1;
	}

}
