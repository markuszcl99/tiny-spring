package com.tiny.spring.batis;

import com.tiny.spring.beans.factory.InitializingBean;
import com.tiny.spring.beans.factory.annotation.Autowired;
import com.tiny.spring.jdbc.core.JdbcTemplate;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author: markus
 * @date: 2023/11/6 8:06 AM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory, InitializingBean {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String mapperLocations;

    private Map<String, MapperNode> mapperNodeMap = new HashMap<>();

    public DefaultSqlSessionFactory() {

    }

    public void init() {
        scanLocation(this.mapperLocations);
    }

    private void scanLocation(String location) {
        String sLocationPath = this.getClass().getClassLoader().getResource(location).getPath();
        File dir = new File(sLocationPath);
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                scanLocation(location + "/" + file.getName());
            } else {
                buildMapperNode(location + "/" + file.getName());
            }
        }
    }

    private Map<String, MapperNode> buildMapperNode(String filePath) {
        System.out.println(filePath);
        SAXReader saxReader = new SAXReader();
        URL xmlPath = this.getClass().getClassLoader().getResource(filePath);
        try {
            Document document = saxReader.read(xmlPath);
            Element rootElement = document.getRootElement();
            String namespace = rootElement.attributeValue("namespace");
            Iterator<Element> nodes = rootElement.elementIterator();
            while (nodes.hasNext()) {
                Element node = nodes.next();
                String id = node.attributeValue("id");
                String parameterType = node.attributeValue("parameterType");
                String resultType = node.attributeValue("resultType");
                String sql = node.getText();

                MapperNode selectNode = new MapperNode();
                selectNode.setNamespace(namespace);
                selectNode.setId(id);
                selectNode.setParameterType(parameterType);
                selectNode.setSql(sql);
                selectNode.setResultType(resultType);

                this.mapperNodeMap.put(namespace + "." + id, selectNode);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return this.mapperNodeMap;
    }

    public String getMapperLocations() {
        return mapperLocations;
    }

    public void setMapperLocations(String mapperLocations) {
        this.mapperLocations = mapperLocations;
    }

    public void setMapperNodeMap(Map<String, MapperNode> mapperNodeMap) {
        this.mapperNodeMap = mapperNodeMap;
    }

    @Override
    public SqlSession openSession() {
        SqlSession sqlSession = new DefaultSqlSession();
        sqlSession.setJdbcTemplate(this.jdbcTemplate);
        sqlSession.setSqlSessionFactory(this);
        return sqlSession;
    }

    @Override
    public MapperNode getMapperNode(String name) {
        return this.mapperNodeMap.get(name);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        // 属性赋值后，进行mapper node内容初始化
        init();
    }
}
