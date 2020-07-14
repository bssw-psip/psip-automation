package io.bssw.psip;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import io.bssw.psip.backend.data.Activity;
import io.bssw.psip.backend.data.Category;
import io.bssw.psip.backend.data.Content;
import io.bssw.psip.backend.data.Item;
import io.bssw.psip.backend.data.Score;

public class GenData {
	public void run(boolean verify) {
		Yaml yaml = new Yaml(new Constructor(Content.class));
		InputStream inputStream = this.getClass()
				  .getClassLoader()
				  .getResourceAsStream("content.yml");
		Content content = yaml.load(inputStream);
		
		if (!verify) {
			try {
				BufferedWriter bw = Files.newBufferedWriter(Paths.get("src/main/resources/import.sql"));
				PrintWriter writer = new PrintWriter(bw);
				int activity_id = 1;
				for (Activity activity : content.getActivities()) {
					writer.println("insert into activity (id, name, icon, path, description) values (" + activity_id++ + ", '" +
								activity.getName() + "', '" +
								activity.getIcon() + "', '" +
								activity.getPath() +"', '" + 
								activity.getDescription() + "');");
				}
				writer.println("commit;");
				activity_id = 1;
				int score_id = 1;
				for (Activity activity : content.getActivities()) {
					for (Score score : activity.getScores()) {
						writer.println("insert into score (id, name, value, color, activity_id) values (" + 
								score_id++ + ", '" +
								score.getName() + "', " +
								score.getValue() + ", '" +
								score.getColor() + "', " +
								activity_id + ");");
					}
					activity_id++;
				}
				writer.println("commit;");
				activity_id = 1;
				int category_id = 1;
				for (Activity activity : content.getActivities()) {
					for (Category category : activity.getCategories()) {
						writer.println("insert into category (id, name, activity_id, icon, path, description) values (" + category_id++ + ", '" +
								category.getName() + "', " +
								activity_id + ", '', '" +
								category.getPath() +"', \n  '" + 
								category.getDescription() + "');");
					}
					activity_id++;
				}
				writer.println("commit;");
				category_id = 1;
				int item_id = 1;
				for (Activity activity : content.getActivities()) {
					for (Category category : activity.getCategories()) {
						for (Item item : category.getItems()) {
							writer.println("insert into item (id, name, category_id, icon, path, description) values (" + 
									item_id++ + ", '" +
									item.getName() + "', " +
									category_id + ", '', '" +
									item.getPath() +"', \n  '" + 
									item.getDescription() + "');");
						}
						category_id++;
					}
				}
				writer.println("commit;");
				item_id = 1;
				for (Activity activity : content.getActivities()) {
					for (Category category : activity.getCategories()) {
						for (Item item : category.getItems()) {
							for (String question : item.getQuestions()) {
								writer.println("insert into item_questions (item_id, questions) values (" + 
										item_id + ", '" +
										question + "');");
							}
							item_id++;
						}
					}
				}
				writer.println("commit;");
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public static void main(String[] args) {
		boolean verify = args.length == 0 || args[0].equals("--verify");
		GenData gen = new GenData();
		gen.run(verify);
	}
}
