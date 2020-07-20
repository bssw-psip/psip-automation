package io.bssw.psip;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import io.bssw.psip.backend.data.Activity;
import io.bssw.psip.backend.data.Category;
import io.bssw.psip.backend.data.Content;
import io.bssw.psip.backend.data.Item;
import io.bssw.psip.backend.data.Score;

public class GenData {
	public void run(String input, Optional<String> output) throws IOException {
		Yaml yaml = new Yaml(new Constructor(Content.class));
		InputStream inputStream = FileUtils.openInputStream(new File(input));
		Content content = yaml.load(inputStream);
		
		if (output.isPresent()) {
			BufferedWriter bw = Files.newBufferedWriter(Paths.get(output.get()));
			PrintWriter writer = new PrintWriter(bw);
			int activity_id = 1;
			for (Activity activity : content.getActivities()) {
				writer.println("insert into activity (id, name, icon, path, description) values (" + activity_id++ + ", '" +
							activity.getName() + "', '" +
							activity.getIcon() + "', '" +
							activity.getPath() + "', '" + 
							activity.getDescription() + "');");
			}
			writer.println("commit;");
			activity_id = 1;
			int score_id = 1;
			for (Activity activity : content.getActivities()) {
				for (Score score : activity.getScores()) {
					writer.println("insert into score (id, name, boost, value, color, activity_id) values (" + 
							score_id++ + ", '" +
							score.getName() + "', '" +
							score.getBoost() + "', " +
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
							category.getPath() + "', '" + 
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
								item.getPath() + "', '" + 
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
		}
		
	}
	
	public static void main(String[] args) {
		Option input = Option.builder("i").longOpt("input").hasArg().required().build();
		Option output = Option.builder("o").longOpt("output").hasArg().build();
		Options options = new Options();
		options.addOption(input);
		options.addOption(output);
		CommandLineParser parser = new DefaultParser();
		
		try {
			CommandLine commandLine = parser.parse(options, args);
			GenData gen = new GenData();
			gen.run(commandLine.getOptionValue("i"), Optional.ofNullable(commandLine.getOptionValue("o")));
		} catch (ParseException e) {
            System.out.print("Parse error: ");
            System.out.println(e.getLocalizedMessage());
        } catch (IOException e) {
        	System.out.println(e.getLocalizedMessage());
        }
	}
}
