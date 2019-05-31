package com.sr03.rest;

import com.sr03.dao.DAOFactory;
import com.sr03.dao.QuizDAO;
import com.sr03.entities.AnswerEntity;
import com.sr03.entities.QuestionEntity;
import com.sr03.entities.QuizEntity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

@Path("QuizService")
public class QuizRest {
    private QuizDAO quizDAO;

    public QuizRest() {
        this.quizDAO = DAOFactory.getInstance().getQuizDAO();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_XML)
    public String getQuiz(@PathParam("id") Long id) {
        QuizEntity quiz = quizDAO.get(id);

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return "";
        }

        Document document = documentBuilder.newDocument();

        Element root = document.createElement("quiz");
        root.setAttribute("subject", quiz.getSubject().getName());
        root.setAttribute("id", quiz.getId().toString());
        document.appendChild(root);

        for (QuestionEntity question : quiz.getQuestions()) {
            Element questionElt = document.createElement("question");
            questionElt.setAttribute("id", question.getId().toString());
            Element body = document.createElement("contenu");
            body.appendChild(document.createTextNode(question.getBody()));
            questionElt.appendChild(body);

            for (AnswerEntity answer : question.getAnswers()) {
                Element answerElt = document.createElement("answer");
                answerElt.setAttribute("correct", answer.getIs_correct().toString());
                answerElt.appendChild(document.createTextNode(answer.getBody()));
                questionElt.appendChild(answerElt);
            }

            root.appendChild(questionElt);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "no");
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
            return "";
        }

        StringWriter stringWriter = new StringWriter();

        try {
            transformer.transform(new DOMSource(document), new StreamResult(stringWriter));
        } catch (TransformerException e) {
            e.printStackTrace();
            return "";
        }

        return stringWriter.toString();
    }
}
