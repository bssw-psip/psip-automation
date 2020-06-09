create sequence hibernate_sequence start with 1 increment by 1
create table activity (id bigint not null, description TEXT, icon varchar(255), name varchar(255), path varchar(255), primary key (id))
create table activity_categories (activity_id bigint not null, categories_id bigint not null)
create table category (id bigint not null, description TEXT, icon varchar(255), name varchar(255), path varchar(255), activity_id bigint, primary key (id))
create table category_items (category_id bigint not null, items_id bigint not null)
create table item (id bigint not null, advanced_description TEXT, basic_description TEXT, description TEXT, icon varchar(255), intermediate_description TEXT, name varchar(255), path varchar(255), score integer, category_id bigint, primary key (id))
alter table activity_categories add constraint UK_q2cn89ihl94lshxsymeknry1p unique (categories_id)
alter table category_items add constraint UK_fompgmgeqyqrfab7o3hknm8le unique (items_id)
alter table activity_categories add constraint FKnqhq2mu0mphh4yagpthaqda1m foreign key (categories_id) references category
alter table activity_categories add constraint FK529xo03v934dmmsatj25aus6i foreign key (activity_id) references activity
alter table category add constraint FKdgejwxouqepc1gis4eepmtocd foreign key (activity_id) references activity
alter table category_items add constraint FKquqbl7rjn095s3u1251lirnob foreign key (items_id) references item
alter table category_items add constraint FKndv81ar0pvens33imsg56sovg foreign key (category_id) references category
alter table item add constraint FK2n9w8d0dp4bsfra9dcg0046l4 foreign key (category_id) references category
insert into activity (id, name, icon, path, description)  values (1, 'Assessment', 'CLIPBOARD_CHECK', 'assessment', 'The assessment section is used to estimate the degree to which software engineering practices are currently being used by your project.')
insert into activity (id, name, icon, path, description)  values (2, 'Tracking', 'TASKS', 'tracking', 'The tracking section is used to estimate the degree to which software engineering practices are currently being used by your project.')
insert into activity (id, name, icon, path, description)  values (3, 'Integration', 'COGS', 'integration', 'The integration section is used to aid the integration of progress tracking cards into your development workflow.')
commit
insert into category (id, name, icon, path, description)  values (1, 'Better Development', '', 'development', 
	'Improving scientific software development—a process of writing, maintaining, and extending code—in order to increase software productivity, 
	quality, and sustainability, as key aspects of advancing overall scientific productivity')
insert into category (id, name, icon, path, description)  values (2, 'Better Planning', '', 'planning', 
	'Improving strategies for planning in order to increase software productivity, quality, and sustainability, while underscoring the 
	unique characteristics of scientific software, including that requirements often change during discovery.')
insert into category (id, name, icon, path, description)  values (3, 'Better Performance', '', 'performance', 
	'Improving strategies for writing scientific software that is efficient, scalable, and portable—from laptops to emerging extreme-scale 
	architectures—while preserving other code quality metrics such as correctness and usability.')
insert into category (id, name, icon, path, description)  values (4, 'Better Reliability', '', 'reliability', 
	'Improving methods of testing and verification to ensure that software is robust and produces reliable results, thereby 
	helping to ensure the integrity of research and enable collaboration across teams.')
insert into category (id, name, icon, path, description)  values (5, 'Better Collaboration', '', 'collaboration', 
	'Improving ways to facilitate work across teams and to promote partnerships and community contributions via software. 
	We consider projects of all sizes, from small teams through multi-institutional aggregate teams.')
insert into category (id, name, icon, path, description)  values (6, 'Better Development', '', 'development', 
	'Improving scientific software development—a process of writing, maintaining, and extending code—in order to increase software productivity, 
	quality, and sustainability, as key aspects of advancing overall scientific productivity')
insert into category (id, name, icon, path, description)  values (7, 'Better Planning', '', 'planning', 
	'Improving strategies for planning in order to increase software productivity, quality, and sustainability, while underscoring the 
	unique characteristics of scientific software, including that requirements often change during discovery.')
insert into category (id, name, icon, path, description)  values (8, 'Better Performance', '', 'performance', 
	'Improving strategies for writing scientific software that is efficient, scalable, and portable—from laptops to emerging extreme-scale 
	architectures—while preserving other code quality metrics such as correctness and usability.')
insert into category (id, name, icon, path, description)  values (9, 'Better Reliability', '', 'reliability', 
	'Improving methods of testing and verification to ensure that software is robust and produces reliable results, thereby 
	helping to ensure the integrity of research and enable collaboration across teams.')
insert into category (id, name, icon, path, description)  values (10, 'Better Collaboration', '', 'collaboration', 
	'Improving ways to facilitate work across teams and to promote partnerships and community contributions via software. 
	We consider projects of all sizes, from small teams through multi-institutional aggregate teams.')
commit
insert into item (id, name, icon, path, description, basic_description, intermediate_description, advanced_description)  values (1, 'Revision Control', '', 'revision_control', 
	'Approaches for managing changes to files (source code, documentation, data) as well as maintaining their history and attribution, 
	especially across multiple contributors and branches of development (also known as version control).', 
	'Uses revision control system such as Git', 
	'Uses a basic development workflow (e.g. basic Git workflow)', 
	'Uses an advanced development workflow (e.g. Gitflow)')
insert into item (id, name, icon, path, description, basic_description, intermediate_description, advanced_description)  values (2, 'Code Reviews', '', 'code_reviews', 
	'The act of consciously and systematically convening with one''s fellow programmers to check each other''s code for mistakes, and has 
	been repeatedly shown to accelerate and streamline the process of software development like few other practices can.', 
	'Ad-hoc code reviews', 
	'Regular code reviews (e.g. weekly meetings)', 
	'Code reviews automated in workflow (e.g. via pull requests)')
insert into item (id, name, icon, path, description, basic_description, intermediate_description, advanced_description)  values (3, 'Issue Tracking', '', 'issue_tracking', 
	'The process of capturing, reporting, tracking, and managing information about issues related to software. Issues include bugs, 
	feature requests, missing documentation, and other problems and/or requirements.', 
	'Manual issue tracking via email or other medium', 
	'Dedicated issue tracking system being used', 
	'Integrated issue tracking  (e.g. pull requests)')
insert into item (id, name, icon, path, description, basic_description, intermediate_description, advanced_description)  values (4, 'Deployment', '', 'deployment', 
	'', 
	'Manual deployment using a script', 
	'Deployment as part of development workflow with manual intervention', 
	'Continuous deployment')
insert into item (id, name, icon, path, description, basic_description, intermediate_description, advanced_description)  values (5, 'Documentation', '', 'documentation', 
	'Creating, maintaining, and hosting quality documentation (written text or illustration) about the use, operation, or design of software.', 
	'Ad-hoc text files', 
	'Code and documentation are cross referenced and updated when committed to repository', 
	'Integrated with the package release workflow')
insert into item (id, name, icon, path, description, basic_description, intermediate_description, advanced_description)  values (6, 'Development Process', '', 'development_process', 
	'', 
	'Has development process but it is based on ad-hoc rules', 
	'Employs an iterative development process', 
	'Uses an agile development methodology')
insert into item (id, name, icon, path, description, basic_description, intermediate_description, advanced_description)  values (7, 'Due Diligence', '', 'due_diligence', 
	'', 
	'Formal guidelines for accepting contributions', 
	'Clearly defined standards for coding and documentation', 
	'Provenance and license checking for contributions')
insert into item (id, name, icon, path, description, basic_description, intermediate_description, advanced_description)  values (8, 'Software Design', '', 'software_design', 
	'Major considerations in designing software to meet its scientific objectives and sustainability goals. Because requirements for 
	scientific software often change during scientific discovery, design tends to evolve over time.', 
	'Development guidelines include design in the process', 
	'A modeling language is employed for key aspects of the project', 
	'isual modeling using a graphical representation to capture design')
insert into item (id, name, icon, path, description, basic_description, intermediate_description, advanced_description)  values (9, 'Onboarding', '', 'onboarding', 
	'', 
	'Initial onboarding process is documented', 
	'Used for supervisors for new hires', 
	'Used for all personnel changes')
insert into item (id, name, icon, path, description, basic_description, intermediate_description, advanced_description)  values (10, 'Requirements Analysis', '', 'requirements_analysis', 
	'Statements about what functions a software product shall perform, including any constraints under which it shall operate but 
	avoiding as much as possible entanglements in how it shall be implemented.', 
	'Development guidelines include requirements gathering', 
	'Formal requirements gathering is undertaken as part of the project', 
	'Requirements management process is employed')
insert into item (id, name, icon, path, description, basic_description, intermediate_description, advanced_description)  values (11, 'Testing', '', 'testing', 
	'The process of ensuring that a software product is meeting its requirements at each stage of development, delivery, and deployment. 
	Testing helps to avoid defects and verify correctness.', 
	'Comparison used to create system-level no-change tests', 
	'Unit testing for refactored and new code', 
	'Continuous integration')
insert into item (id, name, icon, path, description, basic_description, intermediate_description, advanced_description)  values (12, 'Reproducibility', '', 'reproducibility', 
	'Any effort whose goal is to increase trustworthiness and reuse of computational capabilities and results and to ensure correctness.', 
	'Publication of code', 
	'Inclusion of data when code is published', 
	'Automatic provenance capture system is employed')
commit
insert into activity_categories (activity_id, categories_id) values (1, 1)
insert into activity_categories (activity_id, categories_id) values (1, 2)
insert into activity_categories (activity_id, categories_id) values (1, 3)
insert into activity_categories (activity_id, categories_id) values (1, 4)
insert into activity_categories (activity_id, categories_id) values (1, 5)
insert into activity_categories (activity_id, categories_id) values (2, 6)
insert into activity_categories (activity_id, categories_id) values (2, 7)
insert into activity_categories (activity_id, categories_id) values (2, 8)
insert into activity_categories (activity_id, categories_id) values (2, 9)
insert into activity_categories (activity_id, categories_id) values (2, 10)
commit
insert into category_items (category_id, items_id) values (1, 1)
insert into category_items (category_id, items_id) values (1, 2)
insert into category_items (category_id, items_id) values (1, 3)
insert into category_items (category_id, items_id) values (1, 4)
insert into category_items (category_id, items_id) values (1, 5)
insert into category_items (category_id, items_id) values (2, 6)
insert into category_items (category_id, items_id) values (2, 7)
insert into category_items (category_id, items_id) values (2, 8)
insert into category_items (category_id, items_id) values (2, 9)
insert into category_items (category_id, items_id) values (2, 10)
insert into category_items (category_id, items_id) values (3, 11)
insert into category_items (category_id, items_id) values (3, 12)
commit
