package org;

import hw37.Order;
import hw37.OrderServlet;
import hw37.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;



public class OrderServletTest {

    private OrderServlet servlet;

    @BeforeEach
    public void setUp() {
        servlet = new OrderServlet();
    }

    @Test
    public void testDoPost_createsOrder() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(outContent, true);
        when(response.getWriter()).thenReturn(writer);

        servlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_CREATED);
        String responseText = outContent.toString();
        assertTrue(responseText.contains("Order created with ID"));
    }

    @Test
    public void testDoGet_existingOrder() throws IOException, ServletException {
        Product product = new Product(1, "TestProduct", 5.0);
        Order order = new Order(1, new Date(), 5.0, Collections.singletonList(product));
        servlet.orderMap.put(1, order);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getPathInfo()).thenReturn("/1");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(outContent, true);
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);

        String responseText = outContent.toString();
        assertTrue(responseText.contains("Order ID: 1"));
        assertTrue(responseText.contains("TestProduct"));
    }

    @Test
    public void testDoGet_orderNotFound() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getPathInfo()).thenReturn("/999");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(outContent, true);
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_NOT_FOUND);
        assertTrue(outContent.toString().contains("Order not found"));
    }

    @Test
    public void testDoPut_updatesOrder() throws IOException, ServletException {
        Product product = new Product(1, "Old", 5.0);
        servlet.orderMap.put(2, new Order(2, new Date(), 5.0, Collections.singletonList(product)));

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getPathInfo()).thenReturn("/2");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(outContent, true);
        when(response.getWriter()).thenReturn(writer);

        servlet.doPut(request, response);

        assertTrue(outContent.toString().contains("Order updated"));
    }

    @Test
    public void testDoDelete_existingOrder() throws IOException, ServletException {
        servlet.orderMap.put(3, new Order(3, new Date(), 0.0, Collections.emptyList()));

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getPathInfo()).thenReturn("/3");

        servlet.doDelete(request, response);

        verify(response).setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Test
    public void testDoDelete_orderNotFound() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getPathInfo()).thenReturn("/404");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(outContent, true);
        when(response.getWriter()).thenReturn(writer);

        servlet.doDelete(request, response);

        verify(response).setStatus(HttpServletResponse.SC_NOT_FOUND);
        assertTrue(outContent.toString().contains("Order not found"));
    }
}
