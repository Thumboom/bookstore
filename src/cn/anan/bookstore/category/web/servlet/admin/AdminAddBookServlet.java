package cn.anan.bookstore.category.web.servlet.admin;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.anan.bookstore.book.domain.Book;
import cn.anan.bookstore.book.service.BookService;
import cn.anan.bookstore.category.domain.Category;
import cn.anan.bookstore.category.service.CategoryService;
import cn.itcast.commons.CommonUtils;

/**
 * Servlet implementation class AdminAddBookServlet
 */
@WebServlet("/admin/AdminAddBookServlet")
public class AdminAddBookServlet extends HttpServlet {
	
	
	private CategoryService categoryService = new CategoryService();
	private BookService bookService = new BookService();
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		DiskFileItemFactory factory = new DiskFileItemFactory(15 * 1024, new File("E:/e/temp"));
		ServletFileUpload sfu = new ServletFileUpload(factory);
		
		sfu.setFileSizeMax(20 * 1024);
		System.out.println("hey.....");
		try {
			List<FileItem> fileItemList = sfu.parseRequest(request);
			Map<String, String> map = new HashMap<String, String>();
			for( FileItem fileItem: fileItemList) {
				if(fileItem.isFormField()) {
					map.put(fileItem.getFieldName(), fileItem.getString("UTF-8"));
				}
			}
			
			Book book = CommonUtils.toBean(map, Book.class);
			
			book.setBid(CommonUtils.uuid());
			
			Category category = CommonUtils.toBean(map, Category.class);
			book.setCategory(category);
			
			String savepath = this.getServletContext().getRealPath("/book_img");
			String filename = CommonUtils.uuid() + "_" + fileItemList.get(1).getName();
			
			if(!filename.toLowerCase().endsWith("jpg")) {
				request.setAttribute("msg", "你上传的图片不是jpg格式");
				request.setAttribute("categoryList", categoryService.findAll());
				request.getRequestDispatcher("/adminjsps/admin/book/add.jsp")
					.forward(request, response);
				return;
			}
			
			
			File destFile = new File(savepath, filename);
			
			fileItemList.get(1).write(destFile);
			
			book.setImage("book_img/" + filename);
			
			bookService.add(book);
			
			Image image = new ImageIcon(destFile.getAbsolutePath()).getImage();
			
			if( image.getWidth(null) > 200 || image.getHeight(null) > 200) {
				destFile.delete();
				request.setAttribute("msg", "您上传的图片尺寸超过 200 * 200！");
				request.setAttribute("categoryList", categoryService.findAll());
				request.getRequestDispatcher("/adminjsps/admin/book/add.jsp")
						.forward(request, response);
				return;
			}
				
			System.out.println("here .....");
			request.getRequestDispatcher("/admin/AdminBookServlet?method=findAll")
					.forward(request, response);
			
		} catch (Exception e) {

			if( e instanceof FileUploadBase.FileSizeLimitExceededException) {
				request.setAttribute("msg", "您上传的文件超出了15KB");
				request.setAttribute("categoryList", categoryService.findAll());
				request.getRequestDispatcher("/adminjsps/admin/book/add.jsp")
						.forward(request, response);
			}
			
			e.printStackTrace();
		}
		
	}

}
