package test;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.HeadingTag;
import org.htmlparser.tags.ParagraphTag;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;


public class ZhilianHtmlParser {
	public static void main(String args[]){
		try {
			Parser parser = new Parser("http://www.job5156.com/resume/view/7942569?keyword=ME;M");
			if(null != parser){
				// 根据条件过滤出有关公司的页面区域
				HasAttributeFilter filter = new HasAttributeFilter("class","wrap");
				NodeIterator iterator = parser.extractAllNodesThatMatch (filter).elements ();
				
				
				while(iterator.hasMoreNodes()){
					Node node = iterator.nextNode();
					if(null!=node){
						//System.out.println(node.toHtml());
						getResumeInfo(node);
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void getResumeInfo(Node node){
		if (node instanceof TextNode) {
		} else if (node instanceof TagNode) {
			if(node instanceof Div){
				Div div = (Div)node;
				if("infoBlock".equals(div.getAttribute("class"))){
					for(Node n:div.getChildrenAsNodeArray()){
						//System.out.println(n.toHtml());
						if(n instanceof TableTag){
							TableTag table = (TableTag)n;
							for(Node t:table.getRows()){
								//System.out.println(t.toHtml());
								Node td1 = table.getRows()[0].getColumns()[0];
								//System.out.println(td1.toHtml());
								for(Node tdItem:td1.getChildren().toNodeArray()){
									//System.out.println(tdItem.toHtml());
									if(tdItem instanceof HeadingTag){
										//System.out.println(tdItem.toPlainTextString());
									}else if(tdItem instanceof ParagraphTag){
										if("lightFont".equals(((ParagraphTag) tdItem).getAttribute("class"))){
											System.out.println(tdItem.toPlainTextString());
										}
									}else if(tdItem instanceof TableTag){
										TableTag table1 = (TableTag)tdItem;
										//System.out.println(table1.toHtml());
										//循环Table获取其他信息
										//其他别的信息按照上面步骤获取
									}
								}
							}
						}
					}
				}
			}
			
			TagNode tag = (TagNode) node;
			NodeList nl = tag.getChildren();
			if (null != nl)
				try {
					for (NodeIterator i = nl.elements(); i.hasMoreNodes();)
						getResumeInfo(i.nextNode());
				} catch (ParserException e) {
					e.printStackTrace();
				}
		}
	
	}
}
