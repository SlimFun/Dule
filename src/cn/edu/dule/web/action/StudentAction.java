package cn.edu.dule.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;

import cn.edu.dule.beans.Book;
import cn.edu.dule.beans.BookInfo;
import cn.edu.dule.beans.QueryResult;
import cn.edu.dule.beans.Student;
import cn.edu.dule.beans.User;
import cn.edu.dule.beans.web.PageView;
import cn.edu.dule.beans.web.UserForm;
import cn.edu.dule.dao.BookDao;
import cn.edu.dule.dao.UserDao;
import cn.edu.dule.service.BookService;
import cn.edu.dule.service.UserService;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("base")
@Namespace("/user")
@Scope("prototype") 
public class StudentAction extends ActionSupport implements SessionAware,RequestAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int PLS_LOGOUT_FIRST = 1;
	private static final int PASSWORD_DONT_EQUALS = 2;
	private static final int USERNAME_HAS_BEAN_USED = 3;
	private static final int EMAIL_HAS_BEAN_USED = 4;
	private static final int USER_NOT_EXIST = 5;
	private static final int PASSWORD_ERROR = 6;
	
	private static final String JSON = "json";
	
	private UserService userService;
	
	private Map<String, Object> msg;
	private int id;
	private String loginName;
	private String password;
	private String confirmPassword;
	private PageView<Book> books;
	private BookService bookService;
	private BookInfo bookInfo;
	private int infoId;
	private PageView<BookInfo> pageView;
	private int currentPage;
	
	private Map<String, Object> session;
	private String error;
	private Map<String, Object> request;
	private String searchInfo;
	private Book book;
	private Student user;
	
	private File file;
    
    private String fileFileName;
    
    private String fileContentType;
    
    private int errorCode;
    
    private UserForm registerForm;
    
	@Override
	public String execute() throws Exception {
		return super.execute();
	}
	
	@Action(value="login",results={@Result(name=SUCCESS,type="redirect",location="/user/mainPage"),@Result(name=ERROR,location="/WEB-INF/page/user/login.jsp")})
	public String login(){
//		msg = new HashMap<String, Object>();
		int flag = 0;
		Student stu = null;
		if(StringUtils.isNumeric(loginName)){
			int id = Integer.valueOf(loginName);
			stu = userService.getStudentById(id);
		}else{
			stu = userService.getStudentByUserName(loginName);
			if(stu == null){
				stu = userService.getStudentByEmail(loginName);
			}
		}
		if(stu==null){
			errorCode = USER_NOT_EXIST;
			return ERROR;
		}else if(stu.getPassword().equals(password)){
			session.put("user", stu);
			return SUCCESS;
		}else{
			errorCode = PASSWORD_ERROR;
			return ERROR;
		}
//		return "json";
	}
	
	@Action(value="confirmPassword",results={@Result(name=SUCCESS, type="redirect", location="/user/changePassword"),
			@Result(name=ERROR, type="redirect", location="/user/editPersonInfo")})
	public String confirmPassword(){
		Student stu = (Student) session.get("user");
		if(stu.getPassword().equals(password)){
			return SUCCESS;
		}else{
			session.put("errorCode", PASSWORD_ERROR);
			return ERROR;
		}
	}
	
	@Action(value="changePassword",results={@Result(name=SUCCESS,location="/WEB-INF/page/user/changePassword.jsp")})
	public String changePassword(){
		if(session.get("errorCode")!=null){
			errorCode = (Integer) session.get("errorCode");
			session.remove("errorCode");
		}
		return SUCCESS;
	}
	
	@Action(value="doChangePassword",results={@Result(name=SUCCESS,type="redirect",location="/user/editPersonInfo"),
			@Result(name=ERROR,type="redirect",location="/user/changePassword")})
	public String doChangePassword(){
		if(!password.equals(confirmPassword)){
			session.put("errorCode", PASSWORD_DONT_EQUALS);
			return ERROR;
		}
		user = (Student) session.get("user");
		user.setPassword(password);
		userService.updateStudent(user);
		session.put("user", user);
		return SUCCESS;
	}
	
	@Action(value="logout",results={@Result(name=SUCCESS,type="redirect",location="/user/mainPage")})
	public String logout(){
		session.remove("user");
		return SUCCESS;
	}
	
	@Action(value="toLogin",results={@Result(name=SUCCESS,location="/WEB-INF/page/user/login.jsp")})
	public String toLogin(){
		return SUCCESS;
	}
	
	@Action(value="listBooksOfOneInfo",results={@Result(name=SUCCESS,location="/WEB-INF/page/user/listBooksOfOneInfo.jsp")})
	public String listBooksOfOneInfo(){
		books = new PageView<Book>(1);
		books.setResult(bookService.getBooksOfOneBookInfo(infoId));
		bookInfo = bookService.getBookInfo(infoId);
		Student user = (Student) session.get("user");
		request.put("focused", user.getFocusOnBooks().contains(bookInfo));
		return SUCCESS;
	}
	
	@Action(value="searchBook",results={@Result(name=SUCCESS,location="/WEB-INF/page/user/listBooks.jsp")})
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
	
	@Action(value="allBooks",results={@Result(name=SUCCESS,location="/WEB-INF/page/user/allBook.jsp"),@Result(name=ERROR,location="/error.jsp")})
	public String manageBook(){
		pageView = new PageView<BookInfo>(currentPage);
		QueryResult<BookInfo> result = bookService.getBookInfos((int) pageView.getStartIndex(), PageView.PAGE_SIZE);
		pageView.setResult(result);
		request.put("startPage", pageView.getStartPage());
		request.put("endPage", pageView.getEndPage());
		request.put("currentPage", pageView.getCurrentPage());
		request.put("totalPage", pageView.getTotalPage());
		return SUCCESS;
	}
	
	@Action(value="borrowedBooks",results={@Result(name=SUCCESS,location="/WEB-INF/page/user/listBooks.jsp")})
	public String borrowedBooks(){
		Student stu = (Student) session.get("user");
		books = new PageView<Book>(currentPage);
		QueryResult<Book> result = bookService.getBorrowedBooks(stu.getId(), (int) books.getStartIndex(), PageView.PAGE_SIZE);
		for(Book book : result.getResultList()){
			book.setLeftDays(60 - daysBetween(book.getBorrowedDate(), new Date()));
		}
		books.setResult(result);
		request.put("startPage", books.getStartPage());
		request.put("endPage", books.getEndPage());
		request.put("currentPage", books.getCurrentPage());
		request.put("totalPage", books.getTotalPage());
		return SUCCESS;
	}
	
	public int daysBetween(Date smdate,Date bdate)  
    {    
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    }
	
	@Action(value="editPersonInfo",results={@Result(name=SUCCESS,location="/WEB-INF/page/user/editPersionInfo.jsp")})
	public String editPersonInfo(){
		user = (Student) session.get("user");
		if(session.get("errorCode") != null){
			errorCode = (Integer) session.get("errorCode");
			session.remove("errorCode");
		}
		return SUCCESS;
	}

	@Action(value="doEditPersonInfo",results={@Result(name=SUCCESS,location="/WEB-INF/page/user/editPersionInfo.jsp"),
				@Result(name=ERROR,type="redirect",location="/user/editPersonInfo")})
	public String doEditPersonInfo(){
		Student exitsUser = userService.getStudentByUserName(user.getUsername());
		Student oldUser = (Student) session.get("user");
		if(exitsUser != null && exitsUser.getId()!=oldUser.getId()){
			session.put("errorCode", USERNAME_HAS_BEAN_USED);
			return ERROR;
		}else{
			exitsUser = userService.getStudentByEmail(user.getEmail());
			if(exitsUser != null && exitsUser.getId()!=oldUser.getId()){
				session.put("errorCode", EMAIL_HAS_BEAN_USED);
				return ERROR;
			}
		}
		oldUser.setUsername(user.getUsername());
		oldUser.setEmail(user.getEmail());
		oldUser.setPhone(user.getPhone());
		try {
			if(fileFileName!=null && !StringUtils.isEmpty(fileFileName) && !fileFileName.equals(oldUser.getProfile())){
				uploadFile();
				oldUser.setProfile(fileFileName);
			}
			userService.updateStudent(oldUser);
			session.put("user", oldUser);
			user = oldUser;
			return SUCCESS;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ERROR;
		}
	}
	
	@Action(value="register",results={@Result(name=SUCCESS,location="/WEB-INF/page/user/register.jsp"),
			@Result(name=ERROR,location="/WEB-INF/page/user/main.jsp")})
	public String register(){
		Student stu = (Student) session.get("user");
		if(stu != null){
			errorCode = PLS_LOGOUT_FIRST;
			return ERROR;
		}else{
			return SUCCESS;
		}
	}
	
	@Action(value="doRegister",results={@Result(name=SUCCESS,location="/WEB-INF/page/user/login.jsp"),
			@Result(name=ERROR,location="/WEB-INF/page/user/register.jsp")})
	public String doRegister(){
		Student exitsUser = userService.getStudentByUserName(registerForm.getUsername());
		if(registerForm.getPassword() == null || !registerForm.getPassword().equals(registerForm.getResumePassword())){
			errorCode = PASSWORD_DONT_EQUALS;
			return ERROR;
		}else if(exitsUser != null){
			errorCode = USERNAME_HAS_BEAN_USED;
			return ERROR;
		}else{
			exitsUser = userService.getStudentByEmail(registerForm.getEmail());
			if(exitsUser != null){
				errorCode = EMAIL_HAS_BEAN_USED;
				return ERROR;
			}
			Student stu = registerForm.generateStu();
			userService.registerUser(stu);
			return SUCCESS;
		}
	}
	
	@Action(value="mainPage",results={@Result(name=SUCCESS,location="/WEB-INF/page/user/main.jsp")})
	public String mainPage(){
		return SUCCESS;
	}
	
	@Action(value="focusBook",results={@Result(name=JSON, type=JSON, params={"root","msg"})})
	public String focusBook(){
		msg = new HashMap<String, Object>(); 
		try{
			Student user = (Student) session.get("user");
			userService.focusBook(user, id);
			msg.put("status", 0);
			session.put("user", user);
		}catch (Exception e) {
			e.printStackTrace();
			msg.put("status", 1);
		}
		return JSON;
	}
	
	@Action(value="cancelFocusBook",results={@Result(name=JSON, type=JSON, params={"root","msg"})})
	public String cancelFocusBook(){
		msg = new HashMap<String, Object>(); 
		try{
			Student user = (Student) session.get("user");
			userService.cancelFocus(user, id);
			msg.put("status", 0);
			session.put("user", user);
		}catch (Exception e) {
			e.printStackTrace();
			msg.put("status", 1);
		}
		return JSON;
	}
	
	@Action(value="focusedBooks",results={@Result(name=SUCCESS,location="/WEB-INF/page/user/listBooks.jsp")})
	public String focusedBooks(){
		Student stu = (Student) session.get("user");
		pageView = new PageView<BookInfo>(currentPage);
		QueryResult<BookInfo> bookInfos = new QueryResult<BookInfo>();
		bookInfos.setResultList(new LinkedList<BookInfo>());
		bookInfos.getResultList().addAll(stu.getFocusOnBooks());
		bookInfos.setTotalRecord(stu.getFocusOnBooks().size());
		pageView.setResult(bookInfos);
		request.put("startPage", pageView.getStartPage());
		request.put("endPage", pageView.getEndPage());
		request.put("currentPage", pageView.getCurrentPage());
		request.put("totalPage", pageView.getTotalPage());
		return SUCCESS;
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
	
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Map<String, Object> getMsg() {
		return msg;
	}

	public void setMsg(Map<String, Object> msg) {
		this.msg = msg;
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

	public UserService getUserService() {
		return userService;
	}

	@Resource(name="userServiceImpl")
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	
	public BookService getBookService() {
		return bookService;
	}

	@Resource(name="bookServiceImpl")
	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
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

	public int getInfoId() {
		return infoId;
	}

	public void setInfoId(int infoId) {
		this.infoId = infoId;
	}

	@Override
	public void setRequest(Map<String, Object> request) {
		// TODO Auto-generated method stub
		this.request = request;
	}

	public PageView<BookInfo> getPageView() {
		return pageView;
	}

	public void setPageView(PageView<BookInfo> pageView) {
		this.pageView = pageView;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String getSearchInfo() {
		return searchInfo;
	}

	public void setSearchInfo(String searchInfo) {
		this.searchInfo = searchInfo;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Student getUser() {
		return user;
	}

	public void setUser(Student user) {
		this.user = user;
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

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public UserForm getRegisterForm() {
		return registerForm;
	}

	public void setRegisterForm(UserForm registerForm) {
		this.registerForm = registerForm;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
}
