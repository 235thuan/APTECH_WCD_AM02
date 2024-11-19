package com.t2303e.assignment02.controllers;

import com.t2303e.assignment02.entity.StudentClass;
import com.t2303e.assignment02.service.StudentClassService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(value = "/classes")
public class StudentClassController extends HttpServlet {
    private StudentClassService studentClassService;

    @Override
    public void init() throws ServletException {
        super.init();
        studentClassService = new StudentClassService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null || action.isEmpty()) action = "list";
        switch (action) {
            case "new":
                formNewClass(req, resp);
                break;
            case "edit":
                editClass(req, resp);
                break;
            case "list":
                showListClasses(req, resp);
                break;
        }
    }

    private void showListClasses(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<StudentClass> list = studentClassService.getAll();
        Map<Integer, Integer> studentCounts = new HashMap<>(); // Assuming you have a method to get student counts by class

        for (StudentClass studentClass : list) {
            int count = studentClassService.getStudentCountByClassId(studentClass.getId()); // You need to implement this method
            studentCounts.put(studentClass.getId(), count);
        }

        req.setAttribute("classes", list);
        req.setAttribute("studentCounts", studentCounts); // Pass student counts to JSP
        req.getRequestDispatcher("class/list.jsp").forward(req, resp);
    }

    private void formNewClass(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("class/new.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        switch (action) {
            case "insert":
                insertClass(req, resp);
                break;
            case "update":
                updateClass(req, resp);
                break;
            case "delete":
                deleteClass(req, resp);
                break;
        }
    }

    private void insertClass(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String name = req.getParameter("name");
            StudentClass studentClass = new StudentClass();
            studentClass.setName(name);
            studentClassService.save(studentClass);
            resp.sendRedirect("classes?action=list");
        } catch (Exception e) {
            formNewClass(req, resp);
        }
    }

    private void editClass(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String idStr = req.getParameter("id");
        try {
            int id = Integer.parseInt(idStr); // Parse ID from string
            StudentClass studentClass = studentClassService.getById(id); // Fetch StudentClass by ID
            if (studentClass != null) {
                req.setAttribute("studentClass", studentClass);
                req.getRequestDispatcher("class/edit.jsp").forward(req, resp);
            } else {
                resp.sendRedirect("classes?action=list"); // Redirect to list if not found
            }
        } catch (Exception e) {
            resp.sendRedirect("classes?action=list"); // Redirect to list if ID is invalid
        }
    }

    private void updateClass(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        String name = req.getParameter("name");

        if (name == null || name.trim().isEmpty()) {
            req.setAttribute("error", "Tên lớp học không được để trống.");
            req.getRequestDispatcher("class/edit.jsp").forward(req, resp);
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            StudentClass studentClass = studentClassService.getById(id);
            if (studentClass == null) {
                req.setAttribute("error", "Không tìm thấy lớp học.");
                req.getRequestDispatcher("class/edit.jsp").forward(req, resp);
                return;
            }
            studentClass.setName(name);
            studentClassService.update(studentClass);
            resp.sendRedirect("classes?action=list");
        } catch (NumberFormatException e) {
            req.setAttribute("error", "ID lớp học không hợp lệ.");
            req.getRequestDispatcher("class/edit.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", "Lỗi cập nhật lớp học: " + e.getMessage());
            req.getRequestDispatcher("class/edit.jsp").forward(req, resp);
        }
    }

    private void deleteClass(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");

        if (idStr == null || idStr.trim().isEmpty()) {
            req.setAttribute("error", "ID lớp học không được để trống.");
            req.getRequestDispatcher("class/list.jsp").forward(req, resp);
            return;
        }

        try {
            int id = Integer.parseInt(idStr);

            // Check if the class exists before deletion
            StudentClass studentClass = studentClassService.getById(id);
            if (studentClass != null) {
                studentClassService.delete(id);
                resp.sendRedirect("classes?action=list&message=Xóa lớp học thành công.");
            } else {
                resp.sendRedirect("classes?action=list&error=Không tìm thấy lớp học để xóa.");
            }
        } catch (NumberFormatException e) {
            req.setAttribute("error", "ID lớp học không hợp lệ.");
            req.getRequestDispatcher("class/list.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", "Lỗi khi xóa lớp học: " + e.getMessage());
            req.getRequestDispatcher("class/list.jsp").forward(req, resp);
        }
    }
}
