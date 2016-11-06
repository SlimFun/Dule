package cn.edu.dule.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;

import cn.edu.dule.beans.Account;
import cn.edu.dule.beans.Admin;
import cn.edu.dule.beans.Book;
import cn.edu.dule.beans.BookInfo;
import cn.edu.dule.beans.BookType;
import cn.edu.dule.beans.Mail;
import cn.edu.dule.beans.Message;
import cn.edu.dule.beans.Priority;
import cn.edu.dule.beans.QueryResult;
import cn.edu.dule.beans.Student;
import cn.edu.dule.beans.User;
import cn.edu.dule.beans.Book.Status;
import cn.edu.dule.beans.web.PageView;
import cn.edu.dule.exception.DataNotExistException;
import cn.edu.dule.service.AccountService;
import cn.edu.dule.service.BookService;
import cn.edu.dule.service.UserService;
import cn.edu.dule.utils.EmailUtil;

import com.opensymphony.xwork2.ActionSupport;

import freemarker.template.utility.StringUtil;

@ParentPackage("base")
@Namespace("/user/admin")
@Scope("prototype") 
public class AdminAction extends ActionSupport implements SessionAware , RequestAware{
	
	private UserService userService;
	private Map<String, Object> session;
	private BookService bookService;
	private AccountService accountService;
	
	private static final String JSON = "json";
	private static final String LOGIN_FIRST = "login_first";
	
	private static final int USERNAME_EXISTS = 4;
	private static final int EMAIL_EXISTS = 1;
	private static final int USER_NOT_EXIST = 2;
	private static final int PASSWORD_ERROR = 3;
	private static final int INPUT_TYPE_ERROR = 4;
	private static final int BORROW_BOOK_SUCC = 5;
	private static final int RETURN_BOOK_SUCC = 6;
	private static final int ADD_ADMIN_SUCC = 7;
	private static final int USERNAME_HAS_BEAN_USED =8;
	private static final int EMAIL_HAS_BEAN_USED = 9;
	
	private static final int PASSWORD_DONT_EQUALS = 2;
	
	private static final int UPDATE_ADMIN_SUCC = 10;
	
	private static final int DONT_HAVE_ACCOUNT_YET = 11;
	private static final int ACCOUNT_HAS_BEAN_FROZEN = 12;
	
	private int id;
	private String password;
	private String error;
	private PageView<BookInfo> pageView;
	private int currentPage;
	private int infoId;
	private PageView<Book> books;
	private BookInfo bookInfo;
	private List<BookType> bookTypes;
	private String dateOfPub;
	private String typeId;
	private Book book;
	private int bookId;
	private int userId;
	private String searchInfo;
	private String bookImg;
	private Admin admin;
	private int prioritiesI;
	
	private File file;
    
    private String fileFileName;
    
    private String fileContentType;
	
	private Map<String, Object> msg;
	private Map<String, Object> request;  
	private List<Priority> allPriorities;
	
	private String[] priorities;
	private int errorCode;
	private String confirmPassword;
	
	private String loginName;
	private QueryResult<Admin> admins;
	private Student userSearchResult;
	private int balance;
	
	@Action(value="login",results={@Result(name=SUCCESS,location="/WEB-INF/page/admin/login.jsp")})
	public String toLogin(){
		return SUCCESS;
	}
	
	@Action(value="doLogin",results={@Result(name=SUCCESS,type="redirect",location="/user/admin/mainPage"),@Result(name=ERROR,location="/WEB-INF/page/admin/login.jsp")})
	public String doLogin(){
		int flag = 0;
		Admin admin = null;
		if(StringUtils.isNumeric(loginName)){
			int id = Integer.valueOf(loginName);
			admin = userService.getAdminById(id);
		}else{
			admin = userService.getAdminByUserName(loginName);
			if(admin == null){
				admin = userService.getAdminByEmail(loginName);
			}
		}
		if(admin==null){
			errorCode = USER_NOT_EXIST;
			return ERROR;
		}else if(admin.getPassword().equals(password)){
			session.put("admin", admin);
			session.put("priorities", admin.generatePriorities());
			return SUCCESS;
		}else{
			errorCode = PASSWORD_ERROR;
			return ERROR;
		}
	}
	
	@Action(value="mainPage",results={@Result(name=SUCCESS,location="/WEB-INF/page/admin/main.jsp"),
			@Result(name=ERROR,type="redirect",location="/user/admin/login")})
	public String mainPage(){
		if(session.get("admin")==null)
			return ERROR;
		if(session.get("code") != null){
			errorCode = (Integer) session.get("code");
			session.remove("code");
		}
		return SUCCESS;
	}
	
	@Action(value="manageBook",results={@Result(name=SUCCESS,location="/WEB-INF/page/admin/manageBook.jsp"),
				@Result(name=ERROR,location="/error.jsp"),
				@Result(name=LOGIN_FIRST,type="redirect",location="/user/admin/login")})
	public String manageBook(){
		if(session.get("admin")==null)
			return LOGIN_FIRST;
		pageView = new PageView<BookInfo>(currentPage);
		QueryResult<BookInfo> result = bookService.getBookInfos((int) pageView.getStartIndex(), PageView.PAGE_SIZE);
		pageView.setResult(result);
		request.put("startPage", pageView.getStartPage());
		request.put("endPage", pageView.getEndPage());
		request.put("currentPage", pageView.getCurrentPage());
		request.put("totalPage", pageView.getTotalPage());
		return SUCCESS;
	}
	
	@Action(value="manageAdmins",results={@Result(name=SUCCESS,location="/WEB-INF/page/admin/manageAdmin.jsp")})
	public String serachAdmin(){
		admins = userService.getAllAdmins();
		allPriorities = new ArrayList<Priority>();
		for(Priority pri : Priority.values()){
			allPriorities.add(pri);
		}
		if(session.get("errorCode") != null){
			errorCode = (Integer) session.get("errorCode");
			session.remove("errorCode");
		}
		return SUCCESS;
	}
	
	@Action(value="confirmPassword",results={@Result(name=SUCCESS, type="redirect", location="/user/admin/changePassword"),
			@Result(name=ERROR, type="redirect", location="/user/admin/editPersonInfo")})
	public String confirmPassword(){
		Admin admin = (Admin) session.get("admin");
		if(admin.getPassword().equals(password)){
			return SUCCESS;
		}else{
			session.put("errorCode", PASSWORD_ERROR);
			return ERROR;
		}
	}
	
	@Action(value="changePassword",results={@Result(name=SUCCESS,location="/WEB-INF/page/admin/changePassword.jsp")})
	public String changePassword(){
		if(session.get("errorCode")!=null){
			errorCode = (Integer) session.get("errorCode");
			session.remove("errorCode");
		}
		return SUCCESS;
	}
	
	@Action(value="doChangePassword",results={@Result(name=SUCCESS,type="redirect",location="/user/admin/editPersonInfo"),
			@Result(name=ERROR,type="redirect",location="/user/admin/changePassword")})
	public String doChangePassword(){
		if(!password.equals(confirmPassword)){
			session.put("errorCode", PASSWORD_DONT_EQUALS);
			return ERROR;
		}
		admin = (Admin) session.get("admin");
		admin.setPassword(password);
		userService.updateAdmin(admin);
		session.put("admin", admin);
		return SUCCESS;
	}
	
	@Action(value="manageAccount",results={@Result(name=SUCCESS,location="/WEB-INF/page/admin/manageAccount.jsp")})
	public String manageAccount(){
		if(session.get("errorCode")!=null){
			errorCode = (Integer) session.get("errorCode");
			session.remove("errorCode");
		}
		return SUCCESS;
	}
	
	@Action(value="searchUser",results={@Result(name=SUCCESS,location="/WEB-INF/page/admin/searchUserResult.jsp"),
			@Result(name=ERROR,type="redirect",location="/user/admin/manageAccount")})
	public String searchUser(){
		Student stu = null;
		if(StringUtils.isNumeric(searchInfo)){
			int id = Integer.valueOf(searchInfo);
			stu = userService.getStudentById(id);
		}else{
			stu = userService.getStudentByUserName(searchInfo);
			if(stu == null){
				stu = userService.getStudentByEmail(searchInfo);
			}
		}
		if(stu==null){
			session.put("errorCode", USER_NOT_EXIST);
			return ERROR;
		}else{
			userSearchResult = stu;
			return SUCCESS;
		}
	}
	
	@Action(value="addAccount",results={@Result(name=SUCCESS,type="redirect",location="/user/admin/searchUser?searchInfo=${userId}")})
	public String addAccount(){
		Account account = new Account();
		account.setFrozen(false);
		account.setMoney(balance);
		accountService.addAccount(userId, account);
		
		Message message = new Message();
		message.setHeader("Your account has bean initialized.");
		message.setContent("Your account has bean initialized.<br/>" +
				"Welcome to Dule!<br/>" +
				"Your initial balance is ￥" + balance + "<br/>" +
				"Have a good time!");
		message.setDate(new Date());
		Student user = userService.getStudentById(userId);
		message.setUser(user);
		userService.sendMessages(message);
		sendEmail(message, user);
		
		return SUCCESS;
	}
	
	@Action(value="frozenAccount",results={@Result(name=SUCCESS, type="redirect",location="/user/admin/searchUser?searchInfo=${userId}")})
	public String frozenAccount(){
		accountService.frozenAccount(id);
		Message message = new Message();
		message.setHeader("Your account has bean frozen.");
		message.setContent("Your account has bean frozen.<br/>" +
				"If you want to reactive your account please apply to admin.");
		message.setDate(new Date());
		Student user = userService.getStudentById(userId);
		message.setUser(user);
		userService.sendMessages(message);
		sendEmail(message, user);
		return SUCCESS;
	}
	
	@Action(value="activeAccount",results={@Result(name=SUCCESS, type="redirect",location="/user/admin/searchUser?searchInfo=${userId}")})
	public String activeAccount(){
		accountService.activeAccount(id);
		Message message = new Message();
		message.setHeader("Your account has bean actived.");
		message.setContent("Your account has bean actived.<br/>" +
				"Thanks for using !<br/>" +
				"Have a good time !");
		message.setDate(new Date());
		Student user = userService.getStudentById(userId);
		message.setUser(user);
		userService.sendMessages(message);
		sendEmail(message, user);
		return SUCCESS;
	}
	
	private void sendEmail(Message message, User user){
		Mail mail = new Mail();
		mail.setSubject(message.getHeader());
		mail.setMessage(message.getContent());
		mail.setReceiver(user.getEmail());
		EmailUtil.send(mail);
	}
	
//	@Action(value="doSearchAdmin",results={@Result(name=SUCCESS,type="redirect",location="/user/admin/updateAdminInfo")})
//	public String doSearchAdmin(){
//		Admin admin = null;
//		if(StringUtils.isNumeric(searchInfo)){
//			int id = Integer.valueOf(searchInfo);
//			admin = userService.getAdminById(id);
//		}else{
//			admin = userService.getAdminByUserName(searchInfo);
//			if(admin == null){
//				admin = userService.getAdminByEmail(searchInfo);
//			}
//		}
//		if(admin==null){
//			errorCode = USER_NOT_EXIST;
//			return ERROR;
//		}else{
//			session.put("searchedAdmin", admin);
//			return SUCCESS;
//		}
//	}
	
//	@Action(value="updateAdminInfo",results={@Result(name=SUCCESS,location="/WEB-INF/page/admin/updateAdminInfo.jsp")})
//	public String toUpdateAdminInfo(){
//		this.admin = (Admin) session.get("searchedAdmin");
//		allPriorities = new ArrayList<Priority>();
//		for(Priority pri : Priority.values()){
//			allPriorities.add(pri);
//		}
//		return SUCCESS;
//	}
	
	@Action(value="searchAdmin",results={@Result(name=SUCCESS,location="/WEB-INF/page/admin/searchAdminResult.jsp"),
			@Result(name=ERROR,type="redirect",params={},location="/user/admin/manageAdmins")})
	public String searchAdmin(){
		Admin admin = null;
		if(StringUtils.isNumeric(searchInfo)){
			int id = Integer.valueOf(searchInfo);
			admin = userService.getAdminById(id);
		}else{
			admin = userService.getAdminByUserName(searchInfo);
			if(admin == null){
				admin = userService.getAdminByEmail(searchInfo);
			}
		}
		if(admin==null){
			session.put("errorCode", USER_NOT_EXIST);
			return ERROR;
		}else{
			this.admin = admin;
			allPriorities = new ArrayList<Priority>();
			for(Priority pri : Priority.values()){
				allPriorities.add(pri);
			}
			return SUCCESS;
		}
	}
	
	@Action(value="updateAdminPrio",results={@Result(name=SUCCESS,type="redirect",location="/user/admin/manageAdmins")})
	public String updateAdminPrio(){
		admin = userService.getAdminById(id);
		admin.resetPriorities(priorities);
		userService.updateAdmin(admin);
		Admin sessionAdmin = (Admin) session.get("admin");
		if(sessionAdmin.getId() == admin.getId()){
			session.put("admin", admin);
			session.put("priorities", admin.generatePriorities());
		}
		errorCode = UPDATE_ADMIN_SUCC;
		return SUCCESS;
	}

	public UserService getUserService() {
		return userService;
	}
	
	@Action(value="getBookNumbers",results={@Result(name=JSON, type=JSON, params={"root","msg"})})
	public String getBookNumbers(){
		msg = new HashMap<String, Object>(); 
		try{
			msg.put("status", 0);
			msg.put("totalNumber", bookService.getNumberOfBookOfOneBookInfo(infoId));
			msg.put("onBoardNumber", bookService.getNumberOfBookOfOneBookInfo(infoId,Status.ON_BOARD));
		}catch (Exception e) {
			e.printStackTrace();
			msg.put("status", 1);
		}
		return JSON;
	}
	
	@Action(value="listBooksOfOneInfo",results={@Result(name=SUCCESS,location="/WEB-INF/page/admin/listBooksOfOneInfo.jsp")})
	public String listBooksOfOneInfo(){
		books = new PageView<Book>(1);
		books.setResult(bookService.getBooksOfOneBookInfo(infoId));
		bookInfo = bookService.getBookInfo(infoId);
		return SUCCESS;
	}
	
	@Action(value="toAddBook",
			results={
				@Result(name=SUCCESS,location="/WEB-INF/page/admin/addBook.jsp"),
				@Result(name=ERROR,location="/error.jsp")},
			exceptionMappings={@ExceptionMapping(exception="java.lang.Exception",result="error")})
	public String toAddBook(){
		bookTypes = bookService.getBookTypes();
		session.put("bookTypes", bookTypes);
		return SUCCESS;
	}
	
	@Action(value="addAdmin",results={
			@Result(name=SUCCESS,location="/WEB-INF/page/admin/addAdmin.jsp")})
	public String addAdmin(){
		allPriorities = new ArrayList<Priority>();
		for(Priority pri : Priority.values()){
			allPriorities.add(pri);
		}
		if(session.get("code") !=null ){
			errorCode = (Integer) session.get("code");
			session.remove("code");
		}
		return SUCCESS;
	}
	
	@Action(value="doAddAdmin",results={@Result(name=SUCCESS,type="redirect",location="/user/admin/addAdmin"),
			@Result(name=ERROR,location="/WEB-INF/page/admin/addAdmin.jsp")})
	public String doAddAdmin(){
		if(userService.getAdminByUserName(admin.getUsername()) != null){
			errorCode = USERNAME_EXISTS;
			allPriorities = new ArrayList<Priority>();
			for(Priority pri : Priority.values()){
				allPriorities.add(pri);
			}
			return ERROR;
		}
		if(userService.getAdminByEmail(admin.getEmail()) != null){
			errorCode = EMAIL_EXISTS;
			allPriorities = new ArrayList<Priority>();
			for(Priority pri : Priority.values()){
				allPriorities.add(pri);
			}
			return ERROR;
		}
		for(String priStr : priorities){
			admin.addPriority(Priority.valueOf(priStr));
			System.out.println(priStr);
		}
		admin.addPriority(Priority.RETURN_BOOK);
		admin.addPriority(Priority.BORROW_BOOK);
		admin.setPassword("000000");
		userService.addAdmin(admin);
		session.put("code", ADD_ADMIN_SUCC);
		return SUCCESS;
	}
	
	@Action(value="updateAdmin")
	public String updateAdmin(){
		return SUCCESS;
	}
	
	@Action(value="addBook",
			results={
				@Result(name=SUCCESS,type="redirect",location="/user/admin/manageBook?currentPage=1"),
				@Result(name=ERROR,type="redirect",location="/user/admin/manageBook?currentPage=1")},
			exceptionMappings={@ExceptionMapping(exception="java.lang.Exception",result=ERROR)})
	public String addBook() throws Exception {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
			java.util.Date date = null;
			date = sdf.parse(dateOfPub);
			book.setStatus(Status.ON_BOARD);
			BookType type = new BookType();
			type.setId(Integer.valueOf(typeId));
			book.getBookInfo().setType(type);
			book.getBookInfo().setDateOfPub(date);
			uploadFile();
			book.getBookInfo().setImg(fileFileName);
			BookInfo info = bookService.getBookInfo(book.getBookInfo());
			if(info != null){
				book.setBookInfo(info);
			}else{
				bookService.addBookInfo(book.getBookInfo());
			}
			bookService.addBook(book);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
	
	@Action(value="toUpdateBookInfo",results={@Result(name=SUCCESS,location="/WEB-INF/page/admin/updateBookInfo.jsp")})
	public String toUpdateBookInfo(){
		bookInfo = bookService.getBookInfo(id);
		bookTypes = bookService.getBookTypes();
		return SUCCESS;
	}
	
	@Action(value="updateBookInfo",
			results={
				@Result(name=SUCCESS,type="redirect",location="/user/admin/listBooksOfOneInfo?infoId=${infoId}"),
				@Result(name=ERROR,location="/error.jsp")},
			exceptionMappings={@ExceptionMapping(exception="java.lang.Exception",result=ERROR)})	
	public String updateBookInfo() throws Exception{
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
			Date date = sdf.parse(dateOfPub);
			BookType type = new BookType();
			type.setId(Integer.valueOf(typeId));
			bookInfo.setType(type);
			bookInfo.setDateOfPub(date);
			System.out.println("bookImg:"+bookImg);
			System.out.println("bookInfoId:"+bookInfo.getId());
			infoId = bookInfo.getId();
			System.out.println("fileFileName"+fileFileName);
			if(fileFileName != null){
				uploadFile();
				bookInfo.setImg(fileFileName);
			}else{
				bookInfo.setImg(bookImg);
			}
			bookService.update(bookInfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
	
	@Action(value="deleteBookInfo",results={@Result(name=SUCCESS,type="redirect",location="/user/admin/manageBook?currentPage=1")})
	public String deleteBookInfo(){
		bookService.deleteBooksOfOneInfo(id);
		bookService.deleteBookInfo(id);
		return SUCCESS;
	}
	
	@Action(value="editPersonInfo",results={@Result(name=SUCCESS,location="/WEB-INF/page/admin/editPersionInfo.jsp")})
	public String editPersonInfo(){
		admin = (Admin) session.get("admin");
		if(session.get("errorCode")!=null){
			errorCode = (Integer) session.get("errorCode");
			session.remove("errorCode");
		}
		allPriorities = new ArrayList<Priority>();
		for(Priority pri : Priority.values()){
			allPriorities.add(pri);
		}
		return SUCCESS;
	}
	
	@Action(value="doEditPersonInfo",results={@Result(name=SUCCESS,location="/WEB-INF/page/admin/editPersionInfo.jsp"),
			@Result(name=ERROR,type="redirect",location="/user/admin/editPersonInfo")})
	public String doEditPersonInfo(){
		Admin exitsAdmin = userService.getAdminByUserName(admin.getUsername());
		Admin oldAdmin = (Admin) session.get("admin");
		if(exitsAdmin != null && exitsAdmin.getId()!=oldAdmin.getId()){
			session.put("errorCode", USERNAME_HAS_BEAN_USED);
			return ERROR;
		}else{
			exitsAdmin = userService.getAdminByEmail(admin.getEmail());
			if(exitsAdmin != null && exitsAdmin.getId()!=oldAdmin.getId()){
				session.put("errorCode", EMAIL_HAS_BEAN_USED);
				return ERROR;
			}
		}
		oldAdmin.setUsername(admin.getUsername());
		oldAdmin.setEmail(admin.getEmail());
		oldAdmin.setPhone(admin.getPhone());
		try {
			if(fileFileName!=null && !StringUtils.isEmpty(fileFileName) && !fileFileName.equals(oldAdmin.getProfile())){
				uploadFile();
				oldAdmin.setProfile(fileFileName);
			}
			userService.updateAdmin(oldAdmin);
			session.put("admin", oldAdmin);
			admin = oldAdmin;
			return SUCCESS;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ERROR;
		}
	}
	
	private void uploadFile() throws Exception {
		String root = ServletActionContext.getServletContext().getRealPath("/upload");
        
        InputStream is = new FileInputStream(file);
        
        OutputStream os = new FileOutputStream(new File(root, fileFileName));
        
        System.out.println("fileFileName: " + fileFileName);

        // 因为file是存放在临时文件夹的文件，我们可以将其文件名和文件路径打印出来，看和之前的fileFileName是否相同
        System.out.println("file: " + file.getName());
        System.out.println("file: " + file.getPath());
        
        byte[] buffer = new byte[500];
        int length = 0;
        
        while(-1 != (length = is.read(buffer, 0, buffer.length)))
        {
            os.write(buffer);
        }
        
        os.close();
        is.close();
	}

	@Action(value="toUpdateBook",
			results={
					@Result(name=SUCCESS,location="/WEB-INF/page/admin/updateBook.jsp"),
					@Result(name=ERROR,location="/error.jsp")},
			exceptionMappings={@ExceptionMapping(exception="java.lang.Exception",result=ERROR)})
	public String toUpdateBook(){
		book = bookService.getBook(id);
		bookTypes = bookService.getBookTypes();
		return SUCCESS;
	}
	
	@Action(value="updateBook",
			results={
				@Result(name=SUCCESS,type="redirect",location="/user/admin/listBooksOfOneInfo?infoId=${infoId}"),
				@Result(name=ERROR,location="/error.jsp")},
			exceptionMappings={@ExceptionMapping(exception="java.lang.Exception",result=ERROR)})	
	public String updateBook() throws Exception{
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
			java.util.Date date = null;
			date = sdf.parse(dateOfPub);
			book.setStatus(Status.ON_BOARD);
			BookType type = new BookType();
			type.setId(Integer.valueOf(typeId));
			book.getBookInfo().setType(type);
			book.getBookInfo().setDateOfPub(date);
			infoId = book.getBookInfo().getId();
			BookInfo info = bookService.getBookInfo(book.getBookInfo());
			if(info != null){
				book.setBookInfo(info);
			}else{
				bookService.addBookInfo(book.getBookInfo());
			}
			bookService.update(book);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
	
	@Action(value="deleteBook",
			results={
				@Result(name=SUCCESS,type="redirect",location="/user/admin/listBooksOfOneInfo?infoId=${infoId}"),
				@Result(name=ERROR,location="/error.jsp")},
			exceptionMappings={@ExceptionMapping(exception="java.lang.Exception",result=ERROR)})
	public String delete(){
		Book book = new Book();
		book.setId(id);
		bookService.delete(book);
		return SUCCESS;
	}

	@Action(value="addBookToInfo",results={@Result(name=SUCCESS,type="redirect",location="/user/admin/listBooksOfOneInfo?infoId=${infoId}")})
	public String addBookToInfo(){
		BookInfo info = bookService.getBookInfo(infoId);
		book.setBookInfo(info);
		book.setStatus(Status.ON_BOARD);
		bookService.addBook(book);
		System.out.println("book Saved");
		return SUCCESS;
	}
	
	@Action(value="searchBook",results={@Result(name=SUCCESS,location="/WEB-INF/page/admin/listBooks.jsp")})
	public String searchBook(){
		if(searchInfo != null){
			if(StringUtils.isNumeric(searchInfo)){
				int bookId = Integer.valueOf(searchInfo);
				book = bookService.getBook(bookId);
			}else{
				QueryResult<BookInfo> result = bookService.searchBookInfo(searchInfo);
				pageView = new PageView<BookInfo>(1);
				pageView.setResult(result);
				request.put("startPage", pageView.getStartPage());
				request.put("endPage", pageView.getEndPage());
				request.put("currentPage", pageView.getCurrentPage());
				request.put("totalPage", pageView.getTotalPage());
			}
		}
		return SUCCESS;
	}
	
	@Action(value="applyForPrio", results={@Result(name=SUCCESS, type="redirect", location="/user/admin/editPersonInfo")})
	public String applyForPrio(){
		Admin admin = (Admin) session.get("admin");
		Message msg = new Message();
		msg.setHeader("An Admin apply for changing priorities.");
		Mail mail = new Mail();
		StringBuffer message = new StringBuffer("An Admin apply for changing the following priorities :<br/>");
		int pri = 0;
		for(Priority p : Priority.values()){
			if(admin.containPriority(p) && !Arrays.asList(priorities).contains(p.toString()) && p != Priority.BORROW_BOOK && p!=Priority.RETURN_BOOK){
				message.append("<font color='#ef5350'>" + p.toString() + "</font><br/>");
			}else if(!admin.containPriority(p) && Arrays.asList(priorities).contains(p.toString())){
				message.append("<font color='#008000'>" + p.toString() + "</font><br/>");
			}
		}
		for(String priStr : priorities){
			Priority p = Priority.valueOf(priStr);
			pri = pri | (1 << p.ordinal());
		}
		pri = pri | 3;
		mail.setSubject(msg.getHeader());
		message.append("<a href='http://localhost:8080/Dule/user/admin/givePriorities?prioritiesI=" + pri + "&id=" + admin.getId() + "'>Ok,I'll change his/her priorities<br/></a>");
		msg.setContent(message.toString());
		mail.setMessage(message.toString());
		msg.setDate(new Date());
		List<Admin> superAdmins = userService.getAllSuperAdmins();
		if(superAdmins != null){
			for(Admin ad : superAdmins){
				mail.setReceiver(ad.getEmail());
				EmailUtil.send(mail);
				msg.setUser(ad);
				userService.sendMessages(msg);
			}
		}
		return SUCCESS;
	}
	
	@Action(value="givePriorities", results={@Result(name=SUCCESS, type="redirect", location="/user/admin/changePrioritiesSucc")})
	public String givePriorities(){
		Admin admin = userService.getAdminById(id);
		admin.setPriority(prioritiesI);
		userService.updateAdmin(admin);
		return SUCCESS;
	}
	
	@Action(value="changePrioritiesSucc", results={@Result(name=SUCCESS, location="/WEB-INF/page/admin/success.jsp")})
	public String changePrioritiesSucc(){
		return SUCCESS;
	}
	
	@Action(value="messages", results={@Result(name=SUCCESS, location="/WEB-INF/page/admin/messages.jsp")})
	public String messages(){
		session.put("newMessageCnt", 0);
		Admin admin = (Admin) session.get("admin");
		List<Message> messages = new LinkedList<Message>();
		messages.addAll(admin.getMessages());
		Collections.sort(messages, new Comparator<Message>() {

			@Override
			public int compare(Message o1, Message o2) {
				// TODO Auto-generated method stub
				if(o1.getDate().getTime() > o2.getDate().getTime()){
					return -1;
				}else if(o1.getDate().getTime() < o2.getDate().getTime()){
					return 1;
				}else{
					return 0;
				}
			}
		});
		request.put("messages", messages);
		return SUCCESS;
	}
	
	@Action(value="BorrowBook",results={@Result(name=SUCCESS,type="redirect",location="/user/admin/mainPage")})
	public String borrowBook(){
		Student user = userService.getStudentById(userId);
		if(user.getAccount() == null){
			session.put("code", DONT_HAVE_ACCOUNT_YET);
			return SUCCESS;
		}else if(user.getAccount().isFrozen()){
			session.put("code", ACCOUNT_HAS_BEAN_FROZEN);
			return SUCCESS;
		}
		bookService.borrowBook(userId, bookId);
		session.put("code", BORROW_BOOK_SUCC);
		return SUCCESS;
	}
	
	@Action(value="ReturnBook",results={@Result(name=SUCCESS,type="redirect",location="/user/admin/mainPage")})
	public String returnBook(){
		try{
			bookService.returnBook(bookId);
			session.put("code", RETURN_BOOK_SUCC);
			return SUCCESS;
		}catch (DataNotExistException e) {
			// TODO: handle exception
			e.printStackTrace();
			return ERROR;
		}
	}
	
	@Resource(name="userServiceImpl")
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public PageView<BookInfo> getPageView() {
		return pageView;
	}

	public void setPageView(PageView<BookInfo> pageView) {
		this.pageView = pageView;
	}

	public BookService getBookService() {
		return bookService;
	}

	@Resource(name="bookServiceImpl")
	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

    public Map<String, Object> getMsg() {  
        return msg;  
    }

	public int getInfoId() {
		return infoId;
	}

	public void setInfoId(int infoId) {
		this.infoId = infoId;
	}

	public PageView<Book> getBooks() {
		return books;
	}

	public void setBooks(PageView<Book> books) {
		this.books = books;
	}

	public BookInfo getBookInfo() {
		return bookInfo;
	}

	public void setBookInfo(BookInfo bookInfo) {
		this.bookInfo = bookInfo;
	}
	
	public List<BookType> getBookTypes() {
		return bookTypes;
	}

	public void setBookTypes(List<BookType> bookTypes) {
		this.bookTypes = bookTypes;
	}
	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public String getDateOfPub() {
		return dateOfPub;
	}

	public void setDateOfPub(String dateOfPub) {
		this.dateOfPub = dateOfPub;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	@Override
	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}


	public String getSearchInfo() {
		return searchInfo;
	}

	public void setSearchInfo(String searchInfo) {
		this.searchInfo = searchInfo;
	}

	public String getBookImg() {
		return bookImg;
	}

	public void setBookImg(String bookImg) {
		this.bookImg = bookImg;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public List<Priority> getAllPriorities() {
		return allPriorities;
	}

	public void setAllPriorities(List<Priority> allPriorities) {
		this.allPriorities = allPriorities;
	}

	public String[] getPriorities() {
		return priorities;
	}

	public void setPriorities(String[] priorities) {
		this.priorities = priorities;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public QueryResult<Admin> getAdmins() {
		return admins;
	}

	public void setAdmins(QueryResult<Admin> admins) {
		this.admins = admins;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Student getUserSearchResult() {
		return userSearchResult;
	}

	public void setUserSearchResult(Student userSearchResult) {
		this.userSearchResult = userSearchResult;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public AccountService getAccountService() {
		return accountService;
	}

	@Resource(name="accountServiceImpl")
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public int getPrioritiesI() {
		return prioritiesI;
	}

	public void setPrioritiesI(int prioritiesI) {
		this.prioritiesI = prioritiesI;
	}
	
}
