package com.minsoo.autocompletedata.service;

import com.google.cloud.language.v1.*;
import com.minsoo.autocompletedata.domain.Refs;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@Service
public class LanguageService {

    public List<Refs> analyzeSentiment(String text){
        List<Refs> wordList = new ArrayList<>();
        LanguageServiceClient language = null;
        try{
            language = LanguageServiceClient.create();

            Document doc = Document.newBuilder()
                    .setContent(text).setType(Document.Type.PLAIN_TEXT).build();

            AnalyzeSyntaxRequest syntaxRequest = AnalyzeSyntaxRequest.newBuilder().setDocument(doc).setEncodingType(EncodingType.UTF8).build();
            AnalyzeSyntaxResponse syntaxResponse = language.analyzeSyntax(syntaxRequest);
            List<Token> tokens = syntaxResponse.getTokensList();
            Refs ref ;
            for (Token token:tokens){
                if(token.getDependencyEdge().getLabel() != DependencyEdge.Label.P && token.getText().getContent().length() > 1) {
                    /*
                    System.out.println("######################");
                    System.out.println("token.getText()=>" + token.getText().getContent());
                    System.out.println("token.getLemma()=>" + token.getLemma());
                    System.out.println("token.getLabel()=>" + token.getDependencyEdge().getLabel());
                    */
                    ref = new Refs();
                    ref.setWord(token.getText().getContent());
                    wordList.add(ref);
                }
            }

        }catch(Exception e){
            e.printStackTrace();

        }finally {
            language.shutdown();
            language.close();
        }
        return wordList;
    }

    public List<Refs> javaTokenizer(String text){

        List<Refs> wordList = new ArrayList<>();
        if(text != null){

            StringTokenizer st = new StringTokenizer(text, " ");
            Refs ref ;
            while(st.hasMoreTokens()){
                String word = st.nextElement().toString();
                if(!"-".equals(word)) {
                    ref = new Refs();
                    ref.setWord(word);
                    wordList.add(ref);
                }
            }
            return wordList;
        }
        return wordList;
    }
}
