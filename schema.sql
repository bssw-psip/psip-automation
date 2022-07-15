create sequence hibernate_sequence start with 1 increment by 1
create table activity (id bigint not null, description TEXT, icon varchar(255), name varchar(255), path varchar(255), primary key (id))
create table category (id bigint not null, description TEXT, icon varchar(255), name varchar(255), path varchar(255), activity_id bigint, primary key (id))
create table item (id bigint not null, advanced_description TEXT, basic_description TEXT, description TEXT, icon varchar(255), intermediate_description TEXT, name varchar(255), path varchar(255), score INTEGER DEFAULT 0, category_id bigint, primary key (id))
create table item_questions (item_id bigint not null, questions varchar(255))
create table score (id bigint not null, color varchar(255), name varchar(255), activity_id bigint, primary key (id))
alter table category add constraint FKdgejwxouqepc1gis4eepmtocd foreign key (activity_id) references activity
alter table item add constraint FK2n9w8d0dp4bsfra9dcg0046l4 foreign key (category_id) references category
alter table item_questions add constraint FKghy98omv8lu675kvlbyks8wdo foreign key (item_id) references item
alter table score add constraint FKogwht6qy0n3bdp3qeh0ecna0n foreign key (activity_id) references activity
insert into activity (id, name, icon, path, description)  values (1, 'Assessment', 'CLIPBOARD_CHECK', 'assessment', 
	'The assessment section is used to estimate the degree to which software engineering practices are currently being used by your project.
	The table below shows your progress in each practice area. Click on the ''Start Here'' button to begin.')
insert into activity (id, name, icon, path, description)  values (2, 'Tracking', 'TASKS', 'tracking', 
	'The tracking section is used to estimate the degree to which software engineering practices are currently being used by your project.')
insert into activity (id, name, icon, path, description)  values (3, 'Integration', 'COGS', 'integration', 
	'The integration section is used to aid the integration of progress tracking cards into your development workflow.')
commit
insert into category (id, name, activity_id, icon, path, description)  values (1, 'Better Development', 1, '', 'development', 
	'Improving scientific software development—a process of writing, maintaining, and extending code—in order to increase software productivity, 
	quality, and sustainability, as key aspects of advancing overall scientific productivity. The table below shows how well your team
	is doing for each practice. Click on the ''Begin Assessment'' button to start assessing your skills.')
insert into category (id, name, activity_id, icon, path, description)  values (2, 'Better Planning', 1, '', 'planning', 
	'Improving strategies for planning in order to increase software productivity, quality, and sustainability, while underscoring the 
	unique characteristics of scientific software, including that requirements often change during discovery. The table below shows how well your team
	is doing for each practice. Click on the ''Begin Assessment'' button to start assessing your skills.')
insert into category (id, name, activity_id, icon, path, description)  values (3, 'Better Performance', 1, '', 'performance', 
	'Improving strategies for writing scientific software that is efficient, scalable, and portable—from laptops to emerging extreme-scale 
	architectures—while preserving other code quality metrics such as correctness and usability. The table below shows how well your team
	is doing for each practice. Click on the ''Begin Assessment'' button to start assessing your skills.')
insert into category (id, name, activity_id, icon, path, description)  values (4, 'Better Reliability', 1, '', 'reliability', 
	'Improving methods of testing and verification to ensure that software is robust and produces reliable results, thereby 
	helping to ensure the integrity of research and enable collaboration across teams. The table below shows how well your team
	is doing for each practice. Click on the ''Begin Assessment'' button to start assessing your skills.')
insert into category (id, name, activity_id, icon, path, description)  values (5, 'Better Collaboration', 1, '', 'collaboration', 
	'Improving ways to facilitate work across teams and to promote partnerships and community contributions via software. 
	We consider projects of all sizes, from small teams through multi-institutional aggregate teams. The table below shows how well your team
	is doing for each practice. Click on the ''Begin Assessment'' button to start assessing your skills.')
insert into category (id, name, activity_id, icon, path, description)  values (6, 'Better Development', 2, '', 'development', 
	'Improving scientific software development—a process of writing, maintaining, and extending code—in order to increase software productivity, 
	quality, and sustainability, as key aspects of advancing overall scientific productivity')
insert into category (id, name, activity_id, icon, path, description)  values (7, 'Better Planning', 2, '', 'planning', 
	'Improving strategies for planning in order to increase software productivity, quality, and sustainability, while underscoring the 
	unique characteristics of scientific software, including that requirements often change during discovery.')
insert into category (id, name, activity_id, icon, path, description)  values (8, 'Better Performance', 2, '', 'performance', 
	'Improving strategies for writing scientific software that is efficient, scalable, and portable—from laptops to emerging extreme-scale 
	architectures—while preserving other code quality metrics such as correctness and usability.')
insert into category (id, name, activity_id, icon, path, description)  values (9, 'Better Reliability', 2, '', 'reliability', 
	'Improving methods of testing and verification to ensure that software is robust and produces reliable results, thereby 
	helping to ensure the integrity of research and enable collaboration across teams.')
insert into category (id, name, activity_id, icon, path, description)  values (10, 'Better Collaboration', 2, '', 'collaboration', 
	'Improving ways to facilitate work across teams and to promote partnerships and community contributions via software. 
	We consider projects of all sizes, from small teams through multi-institutional aggregate teams.')
commit
insert into item (id, name, category_id, icon, path, description, basic_description, intermediate_description, advanced_description)  values (1, 'Revision Control', 1, '', 'revision_control', 
	'Approaches for managing changes to files (source code, documentation, data) as well as maintaining their history and attribution, 
	especially across multiple contributors and branches of development (also known as version control).', 
	'Uses revision control system such as Git', 
	'Uses a basic development workflow (e.g. basic Git workflow)', 
	'Uses an advanced development workflow (e.g. Gitflow)')
insert into item (id, name, category_id, icon, path, description, basic_description, intermediate_description, advanced_description)  values (2, 'Code Reviews', 1, '', 'code_reviews', 
	'The act of consciously and systematically convening with one''s fellow programmers to check each other''s code for mistakes, and has 
	been repeatedly shown to accelerate and streamline the process of software development like few other practices can.', 
	'Ad-hoc code reviews', 
	'Regular code reviews (e.g. weekly meetings)', 
	'Code reviews automated in workflow (e.g. via pull requests)')
insert into item (id, name, category_id, icon, path, description, basic_description, intermediate_description, advanced_description)  values (3, 'Issue Tracking', 1, '', 'issue_tracking', 
	'The process of capturing, reporting, tracking, and managing information about issues related to software. Issues include bugs, 
	feature requests, missing documentation, and other problems and/or requirements.', 
	'Manual issue tracking via email or other medium', 
	'Dedicated issue tracking system being used', 
	'Integrated issue tracking  (e.g. pull requests)')
insert into item (id, name, category_id, icon, path, description, basic_description, intermediate_description, advanced_description)  values (4, 'Deployment', 1, '', 'deployment', 
	'Approaches for versioning, packaging, releasing, and deploying software, documentation, or data for users to then obtain, install, and use.', 
	'Manual deployment using a script', 
	'Deployment as part of development workflow with manual intervention', 
	'Continuous deployment')
insert into item (id, name, category_id, icon, path, description, basic_description, intermediate_description, advanced_description)  values (5, 'Documentation', 1, '', 'documentation', 
	'Creating, maintaining, and hosting quality documentation (written text or illustration) about the use, operation, or design of software.', 
	'Ad-hoc text files', 
	'Code and documentation are cross referenced and updated when committed to repository', 
	'Integrated with the package release workflow')
insert into item (id, name, category_id, icon, path, description, basic_description, intermediate_description, advanced_description)  values (6, 'Development Process', 2, '', 'development_process', 
	'The software development life cycle is the process of dividing software development work into distinct phases to improve design, product management, and project management.', 
	'Has development process but it is based on ad-hoc rules', 
	'Employs an iterative development process', 
	'Uses an agile development methodology')
insert into item (id, name, category_id, icon, path, description, basic_description, intermediate_description, advanced_description)  values (7, 'Due Diligence', 2, '', 'due_diligence', 
	'Tracking and managing contributions to software.', 
	'Formal guidelines for accepting contributions', 
	'Clearly defined standards for coding and documentation', 
	'Provenance and license checking for contributions')
insert into item (id, name, category_id, icon, path, description, basic_description, intermediate_description, advanced_description)  values (8, 'Software Design', 2, '', 'software_design', 
	'Major considerations in designing software to meet its scientific objectives and sustainability goals. Because requirements for 
	scientific software often change during scientific discovery, design tends to evolve over time.', 
	'Development guidelines include design in the process', 
	'A modeling language is employed for key aspects of the project', 
	'isual modeling using a graphical representation to capture design')
insert into item (id, name, category_id, icon, path, description, basic_description, intermediate_description, advanced_description)  values (9, 'Onboarding', 2, '', 'onboarding', 
	'The process of integrating a new employee into an organization or familiarizing a new customer or client with one''s products or services.', 
	'Initial onboarding process is documented', 
	'Used for supervisors for new hires', 
	'Used for all personnel changes')
insert into item (id, name, category_id, icon, path, description, basic_description, intermediate_description, advanced_description)  values (10, 'Requirements Analysis', 2, '', 'requirements_analysis', 
	'Statements about what functions a software product shall perform, including any constraints under which it shall operate but 
	avoiding as much as possible entanglements in how it shall be implemented.', 
	'Development guidelines include requirements gathering', 
	'Formal requirements gathering is undertaken as part of the project', 
	'Requirements management process is employed')
insert into item (id, name, category_id, icon, path, description, basic_description, intermediate_description, advanced_description)  values (11, 'Testing', 3, '', 'testing', 
	'The process of ensuring that a software product is meeting its requirements at each stage of development, delivery, and deployment. 
	Testing helps to avoid defects and verify correctness.', 
	'Comparison used to create system-level no-change tests', 
	'Unit testing for refactored and new code', 
	'Continuous integration')
insert into item (id, name, category_id, icon, path, description, basic_description, intermediate_description, advanced_description)  values (12, 'Reproducibility', 3, '', 'reproducibility', 
	'Any effort whose goal is to increase trustworthiness and reuse of computational capabilities and results and to ensure correctness.', 
	'Publication of code', 
	'Inclusion of data when code is published', 
	'Automatic provenance capture system is employed')
commit
create sequence hibernate_sequence start with 1 increment by 1
create table activity (id bigint not null, description TEXT, icon varchar(255), name varchar(255), path varchar(255), primary key (id))
create table category (id bigint not null, description TEXT, icon varchar(255), name varchar(255), path varchar(255), activity_id bigint, primary key (id))
create table item (id bigint not null, description TEXT, icon varchar(255), name varchar(255), path varchar(255), score integer, category_id bigint, primary key (id))
create table item_questions (item_id bigint not null, questions varchar(255))
create table score (id bigint not null, boost varchar(255), color varchar(255), name varchar(255), "value" integer, activity_id bigint, primary key (id))
alter table category add constraint FKdgejwxouqepc1gis4eepmtocd foreign key (activity_id) references activity
alter table item add constraint FK2n9w8d0dp4bsfra9dcg0046l4 foreign key (category_id) references category
alter table item_questions add constraint FKghy98omv8lu675kvlbyks8wdo foreign key (item_id) references item
alter table score add constraint FKogwht6qy0n3bdp3qeh0ecna0n foreign key (activity_id) references activity
create sequence hibernate_sequence start with 1 increment by 1
create table activity (id bigint not null, description TEXT, icon varchar(255), name varchar(255), path varchar(255), primary key (id))
create table category (id bigint not null, description TEXT, icon varchar(255), name varchar(255), path varchar(255), activity_id bigint, primary key (id))
create table item (id bigint not null, description TEXT, icon varchar(255), name varchar(255), path varchar(255), score integer, category_id bigint, primary key (id))
create table item_questions (item_id bigint not null, questions varchar(255))
create table score (id bigint not null, boost varchar(255), color varchar(255), name varchar(255), "value" integer, activity_id bigint, primary key (id))
alter table category add constraint FKdgejwxouqepc1gis4eepmtocd foreign key (activity_id) references activity
alter table item add constraint FK2n9w8d0dp4bsfra9dcg0046l4 foreign key (category_id) references category
alter table item_questions add constraint FKghy98omv8lu675kvlbyks8wdo foreign key (item_id) references item
alter table score add constraint FKogwht6qy0n3bdp3qeh0ecna0n foreign key (activity_id) references activity
create sequence hibernate_sequence start with 1 increment by 1
create table activity (id bigint not null, description TEXT, icon varchar(255), name varchar(255), path varchar(255), primary key (id))
create table category (id bigint not null, description TEXT, icon varchar(255), name varchar(255), path varchar(255), activity_id bigint, primary key (id))
create table item (id bigint not null, description TEXT, icon varchar(255), name varchar(255), path varchar(255), score integer, category_id bigint, primary key (id))
create table item_questions (item_id bigint not null, questions varchar(255))
create table score (id bigint not null, boost varchar(255), color varchar(255), name varchar(255), "value" integer, activity_id bigint, primary key (id))
alter table category add constraint FKdgejwxouqepc1gis4eepmtocd foreign key (activity_id) references activity
alter table item add constraint FK2n9w8d0dp4bsfra9dcg0046l4 foreign key (category_id) references category
alter table item_questions add constraint FKghy98omv8lu675kvlbyks8wdo foreign key (item_id) references item
alter table score add constraint FKogwht6qy0n3bdp3qeh0ecna0n foreign key (activity_id) references activity
insert into activity (id, name, icon, path, description) values (1, 'Assessment', 'CLIPBOARD_CHECK', 'assessment', 'The assessment section is used to estimate the degree to which software engineering practices are currently being used by your project.')
insert into activity (id, name, icon, path, description) values (2, 'Tracking', 'TASKS', 'tracking', '** COMING SOON ** The tracking section is used to translate your assessment into progress tracking cards (PTCs) so that you can keep track of improvements to you project''''s practices.')
insert into activity (id, name, icon, path, description) values (3, 'Integration', 'COGS', 'integration', '** COMING SOON ** The integration section is used to aid the integration of progress tracking cards into your development workflow.')
commit
insert into score (id, name, boost, "value", color, activity_id) values (1, 'None', 'Can you do better?', 0, '#000000', 1)
insert into score (id, name, boost, "value", color, activity_id) values (2, 'Basic', 'Good start!', 33, '#DC267F', 1)
insert into score (id, name, boost, "value", color, activity_id) values (3, 'Intermediate', 'Keep up the good work!', 66, '#FFB000', 1)
insert into score (id, name, boost, "value", color, activity_id) values (4, 'Advanced', 'Awesome!', 100, '#066999', 1)
commit
insert into category (id, name, activity_id, icon, path, description) values (1, 'Better Development', 1, '', 'development', 'Improving scientific software development—a process of writing, maintaining, and extending code—in order to increase software productivity, quality, and sustainability, as key aspects of advancing overall scientific productivity.')
insert into category (id, name, activity_id, icon, path, description) values (2, 'Better Planning', 1, '', 'planning', 'Improving strategies for planning in order to increase software productivity, quality, and sustainability, while underscoring the unique characteristics of scientific software, including that requirements often change during discovery.')
insert into category (id, name, activity_id, icon, path, description) values (3, 'Better Performance', 1, '', 'performance', 'Improving strategies for writing scientific software that is efficient, scalable, and portable—from laptops to emerging extreme-scale architectures—while preserving other code quality metrics such as correctness and usability.')
insert into category (id, name, activity_id, icon, path, description) values (4, 'Better Reliability', 1, '', 'reliability', 'Improving methods of testing and verification to ensure that software is robust and produces reliable results, thereby helping to ensure the integrity of research and enable collaboration across teams.')
insert into category (id, name, activity_id, icon, path, description) values (5, 'Better Collaboration', 1, '', 'collaboration', 'Improving ways to facilitate work across teams and to promote partnerships and community contributions via software. We consider projects of all sizes, from small teams through multi-institutional aggregate teams.')
commit
insert into item (id, name, category_id, icon, path, description) values (1, 'Revision Control', 1, '', 'revision_control', 'Approaches for managing changes to files (source code, documentation, data) as well as maintaining their history and attribution, especially across multiple contributors and branches of development (also known as version control).')
insert into item (id, name, category_id, icon, path, description) values (2, 'Code Reviews', 1, '', 'code_reviews', 'The act of consciously and systematically convening with one''s fellow programmers to check each other''s code for mistakes, and has been repeatedly shown to accelerate and streamline the process of software development like few other practices can.')
insert into item (id, name, category_id, icon, path, description) values (3, 'Issue Tracking', 1, '', 'issue_tracking', 'The process of capturing, reporting, tracking, and managing information about issues related to software. Issues include bugs, feature requests, missing documentation, and other problems and/or requirements.')
insert into item (id, name, category_id, icon, path, description) values (4, 'Deployment', 1, '', 'deployment', 'Approaches for versioning, packaging, releasing, and deploying software, documentation, or data for users to then obtain, install, and use.')
insert into item (id, name, category_id, icon, path, description) values (5, 'Documentation', 1, '', 'documentation', 'Creating, maintaining, and hosting quality documentation (written text or illustration) about the use, operation, or design of software.')
insert into item (id, name, category_id, icon, path, description) values (6, 'Development Process', 2, '', 'development_process', 'The software development life cycle is the process of dividing software development work into distinct phases to improve design, product management, and project management.')
insert into item (id, name, category_id, icon, path, description) values (7, 'Contribution Management', 2, '', 'contribution_management', 'Tracking and managing contributions to software.')
insert into item (id, name, category_id, icon, path, description) values (8, 'Requirements Analysis', 2, '', 'requirements_analysis', 'Statements about what functions a software product should perform, including any constraints under which it should operate but avoiding as much as possible implementation details.')
insert into item (id, name, category_id, icon, path, description) values (9, 'Software Design', 2, '', 'software_design', 'Major considerations in designing software to meet its scientific objectives and sustainability goals. Software design usually follows from requirements specification, and involves problem solving and planning a software solution.')
insert into item (id, name, category_id, icon, path, description) values (10, 'Onboarding', 2, '', 'onboarding', 'The process of integrating a new employee into an organization or a new team member into a team.')
insert into item (id, name, category_id, icon, path, description) values (11, 'Offboarding', 2, '', 'offboarding', 'The process of transferring employee knowledge to the broader team or organization.')
insert into item (id, name, category_id, icon, path, description) values (12, 'Performance Testing', 3, '', 'performance_testing', 'Determining the speed, scalability, reliability, and stability of an application under a variety of workloads.')
insert into item (id, name, category_id, icon, path, description) values (13, 'Performance Regression Testing', 3, '', 'performance_regression_testing', 'Undertaking testing that exercises performance capabilities combined with plans for how to make practical use of that performance data')
insert into item (id, name, category_id, icon, path, description) values (14, 'Performance Portability', 3, '', 'performance_portability', 'Software exhibiting similar performance across multiple platforms, with the time to solution reflecting efficient utilization of computational resources on each platform.')
insert into item (id, name, category_id, icon, path, description) values (15, 'Testing', 4, '', 'testing', 'The process of ensuring that a software product is meeting its requirements at each stage of development, delivery, and deployment. Testing helps to avoid defects and verify correctness.')
insert into item (id, name, category_id, icon, path, description) values (16, 'Reproducibility', 4, '', 'reproducibility', 'Any effort whose goal is to increase trustworthiness and reuse of computational capabilities and results and to ensure correctness.')
insert into item (id, name, category_id, icon, path, description) values (17, 'Virtual Tools', 5, '', 'virtual_tools', 'The process of documenting and sharing team products and artifacts with virtual tools (e.g. Jira, Google Docs) to communicate goals, objectives.')
insert into item (id, name, category_id, icon, path, description) values (18, 'Telecommuting Communication Practices', 5, '', 'telecommuting_communication_practices', 'The use of video and audio conferencing technology for co-located, hybrid (co-located + remote), or distributed teams.')
commit
insert into item_questions (item_id, questions) values (1, 'Not currently being used')
insert into item_questions (item_id, questions) values (1, 'Code is checked in to a revision control system, such as Git')
insert into item_questions (item_id, questions) values (1, 'Developers make use of pull requests and multiple branches, etc.')
insert into item_questions (item_id, questions) values (1, 'Has a documented workflow for merging code for quality assurance and releases')
insert into item_questions (item_id, questions) values (2, 'Not currently being used')
insert into item_questions (item_id, questions) values (2, 'Ad-hoc code reviews')
insert into item_questions (item_id, questions) values (2, 'Regular code reviews (e.g., weekly meetings)')
insert into item_questions (item_id, questions) values (2, 'Documented process for code reviews to be automated in workflow (e.g., via pull requests)')
insert into item_questions (item_id, questions) values (3, 'Not currently being used')
insert into item_questions (item_id, questions) values (3, 'Ad-hoc issue tracking')
insert into item_questions (item_id, questions) values (3, 'Dedicated issue tracking system consistently being used')
insert into item_questions (item_id, questions) values (3, 'Documented process for how issue tracking is done (e.g. Kanban board, assigned responsibilities)')
insert into item_questions (item_id, questions) values (4, 'Not currently being used')
insert into item_questions (item_id, questions) values (4, 'Manual deployment (e.g., using a script or checklist)')
insert into item_questions (item_id, questions) values (4, 'Deployment as part of development workflow with manual intervention')
insert into item_questions (item_id, questions) values (4, 'Continuous deployment (or deployment combined with continuous integration)')
insert into item_questions (item_id, questions) values (5, 'Not currently being used')
insert into item_questions (item_id, questions) values (5, 'Informally maintained or ad-hoc text files')
insert into item_questions (item_id, questions) values (5, 'Code and documentation are cross-referenced and updated when committed to repository')
insert into item_questions (item_id, questions) values (5, 'Integrated with the software release workflow')
insert into item_questions (item_id, questions) values (6, 'Not currently being used')
insert into item_questions (item_id, questions) values (6, 'Has development process but it is based on ad-hoc rules')
insert into item_questions (item_id, questions) values (6, 'Employs a basic iterative development process')
insert into item_questions (item_id, questions) values (6, 'Follows a responsive, flexible development methodology (e.g. Agile)')
insert into item_questions (item_id, questions) values (7, 'Not currently being used')
insert into item_questions (item_id, questions) values (7, 'Guidelines for accepting contributions')
insert into item_questions (item_id, questions) values (7, 'Clearly defined standards for coding and documentation, including community standards')
insert into item_questions (item_id, questions) values (7, 'Provenance and license checking for contributions')
insert into item_questions (item_id, questions) values (8, 'Not currently being used')
insert into item_questions (item_id, questions) values (8, 'Development guidelines include requirements gathering')
insert into item_questions (item_id, questions) values (8, 'Formal requirements gathering is undertaken as part of the project')
insert into item_questions (item_id, questions) values (8, 'Formally tracked and documented through a requirements management system')
insert into item_questions (item_id, questions) values (9, 'Not currently being used')
insert into item_questions (item_id, questions) values (9, 'Development guidelines include design in the process')
insert into item_questions (item_id, questions) values (9, 'A modeling language is employed for key aspects of the project')
insert into item_questions (item_id, questions) values (9, 'Visual modeling using a graphical representation to capture design')
insert into item_questions (item_id, questions) values (10, 'Not currently being used')
insert into item_questions (item_id, questions) values (10, 'Consistent onboarding process is followed')
insert into item_questions (item_id, questions) values (10, 'Minimally documented onboarding process')
insert into item_questions (item_id, questions) values (10, 'Fully documented onboarding process is used for all personnel changes')
insert into item_questions (item_id, questions) values (11, 'Not currently being used')
insert into item_questions (item_id, questions) values (11, 'Consistent offboarding process is followed')
insert into item_questions (item_id, questions) values (11, 'Minimally documented offboarding process')
insert into item_questions (item_id, questions) values (11, 'Fully documented offboarding process is used for all personnel changes')
insert into item_questions (item_id, questions) values (12, 'Not currently being used')
insert into item_questions (item_id, questions) values (12, 'Conducts performance tests')
insert into item_questions (item_id, questions) values (12, 'Automated performance tests are run regularly')
insert into item_questions (item_id, questions) values (12, 'Uses performance profiling tools to guide performance-related improvements')
insert into item_questions (item_id, questions) values (13, 'Not currently being used')
insert into item_questions (item_id, questions) values (13, 'A testable primary use case and set of measurements are established')
insert into item_questions (item_id, questions) values (13, 'The performance history of the tests are tracked over time')
insert into item_questions (item_id, questions) values (13, 'Performance tracking is run and reviewed at regular intervals on relevant platforms')
insert into item_questions (item_id, questions) values (14, 'Not currently being used')
insert into item_questions (item_id, questions) values (14, 'Utilize a standard parallel programming model (e.g. MPI)')
insert into item_questions (item_id, questions) values (14, 'Utilize a programming model designed for portability (e.g. OpenACC, OpenMP)')
insert into item_questions (item_id, questions) values (14, 'Re-writing the application to utilize an advanced portability library (e.g. Kokkos)')
insert into item_questions (item_id, questions) values (15, 'Not currently being used')
insert into item_questions (item_id, questions) values (15, 'Comparison used to create system-level no-change tests')
insert into item_questions (item_id, questions) values (15, 'Unit testing for refactored and new code')
insert into item_questions (item_id, questions) values (15, 'Continuous integration')
insert into item_questions (item_id, questions) values (16, 'Not currently being used')
insert into item_questions (item_id, questions) values (16, 'Publication of code')
insert into item_questions (item_id, questions) values (16, 'Inclusion of data when code is published')
insert into item_questions (item_id, questions) values (16, 'The entire provenance of the application and its execution environment is published')
insert into item_questions (item_id, questions) values (17, 'Not currently being used')
insert into item_questions (item_id, questions) values (17, 'Used occasionally')
insert into item_questions (item_id, questions) values (17, 'Used on a regualar basis in order to improve processes')
insert into item_questions (item_id, questions) values (17, 'Used strategically to facilitate asynchronous and synchronous communication in order to build trust through transparency')
insert into item_questions (item_id, questions) values (18, 'Not currently being used')
insert into item_questions (item_id, questions) values (18, 'Audio conferencing and/or video conferencing tools for audio only or occasional video use')
insert into item_questions (item_id, questions) values (18, 'Use of video conferencing with defined inclusivity guidelines')
insert into item_questions (item_id, questions) values (18, 'Team awareness of inclusive practices in the use of video conferencing combined with utlizing advanced communication features (e.g. voting, sketch pads)')
commit
create sequence hibernate_sequence start with 1 increment by 1
create table activity (id bigint not null, description TEXT, icon varchar(255), name varchar(255), path varchar(255), primary key (id))
create table category (id bigint not null, description TEXT, icon varchar(255), name varchar(255), path varchar(255), activity_id bigint, primary key (id))
create table item (id bigint not null, description TEXT, icon varchar(255), name varchar(255), path varchar(255), score integer, category_id bigint, primary key (id))
create table item_questions (item_id bigint not null, questions varchar(255))
create table score (id bigint not null, boost varchar(255), color varchar(255), name varchar(255), "value" integer, activity_id bigint, primary key (id))
alter table category add constraint FKdgejwxouqepc1gis4eepmtocd foreign key (activity_id) references activity
alter table item add constraint FK2n9w8d0dp4bsfra9dcg0046l4 foreign key (category_id) references category
alter table item_questions add constraint FKghy98omv8lu675kvlbyks8wdo foreign key (item_id) references item
alter table score add constraint FKogwht6qy0n3bdp3qeh0ecna0n foreign key (activity_id) references activity
insert into activity (id, name, icon, path, description) values (1, 'Assessment', 'CLIPBOARD_CHECK', 'assessment', 'The assessment section is used to estimate the degree to which software engineering practices are currently being used by your project.')
insert into activity (id, name, icon, path, description) values (2, 'Tracking', 'TASKS', 'tracking', '** COMING SOON ** The tracking section is used to translate your assessment into progress tracking cards (PTCs) so that you can keep track of improvements to you project''''s practices.')
insert into activity (id, name, icon, path, description) values (3, 'Integration', 'COGS', 'integration', '** COMING SOON ** The integration section is used to aid the integration of progress tracking cards into your development workflow.')
commit
insert into score (id, name, boost, "value", color, activity_id) values (1, 'None', 'Can you do better?', 0, '#000000', 1)
insert into score (id, name, boost, "value", color, activity_id) values (2, 'Basic', 'Good start!', 33, '#DC267F', 1)
insert into score (id, name, boost, "value", color, activity_id) values (3, 'Intermediate', 'Keep up the good work!', 66, '#FFB000', 1)
insert into score (id, name, boost, "value", color, activity_id) values (4, 'Advanced', 'Awesome!', 100, '#066999', 1)
commit
insert into category (id, name, activity_id, icon, path, description) values (1, 'Better Development', 1, '', 'development', 'Improving scientific software development—a process of writing, maintaining, and extending code—in order to increase software productivity, quality, and sustainability, as key aspects of advancing overall scientific productivity.')
insert into category (id, name, activity_id, icon, path, description) values (2, 'Better Planning', 1, '', 'planning', 'Improving strategies for planning in order to increase software productivity, quality, and sustainability, while underscoring the unique characteristics of scientific software, including that requirements often change during discovery.')
insert into category (id, name, activity_id, icon, path, description) values (3, 'Better Performance', 1, '', 'performance', 'Improving strategies for writing scientific software that is efficient, scalable, and portable—from laptops to emerging extreme-scale architectures—while preserving other code quality metrics such as correctness and usability.')
insert into category (id, name, activity_id, icon, path, description) values (4, 'Better Reliability', 1, '', 'reliability', 'Improving methods of testing and verification to ensure that software is robust and produces reliable results, thereby helping to ensure the integrity of research and enable collaboration across teams.')
insert into category (id, name, activity_id, icon, path, description) values (5, 'Better Collaboration', 1, '', 'collaboration', 'Improving ways to facilitate work across teams and to promote partnerships and community contributions via software. We consider projects of all sizes, from small teams through multi-institutional aggregate teams.')
commit
insert into item (id, name, category_id, icon, path, description) values (1, 'Revision Control', 1, '', 'revision_control', 'Approaches for managing changes to files (source code, documentation, data) as well as maintaining their history and attribution, especially across multiple contributors and branches of development (also known as version control).')
insert into item (id, name, category_id, icon, path, description) values (2, 'Code Reviews', 1, '', 'code_reviews', 'The act of consciously and systematically convening with one''s fellow programmers to check each other''s code for mistakes, and has been repeatedly shown to accelerate and streamline the process of software development like few other practices can.')
insert into item (id, name, category_id, icon, path, description) values (3, 'Issue Tracking', 1, '', 'issue_tracking', 'The process of capturing, reporting, tracking, and managing information about issues related to software. Issues include bugs, feature requests, missing documentation, and other problems and/or requirements.')
insert into item (id, name, category_id, icon, path, description) values (4, 'Deployment', 1, '', 'deployment', 'Approaches for versioning, packaging, releasing, and deploying software, documentation, or data for users to then obtain, install, and use.')
insert into item (id, name, category_id, icon, path, description) values (5, 'Documentation', 1, '', 'documentation', 'Creating, maintaining, and hosting quality documentation (written text or illustration) about the use, operation, or design of software.')
insert into item (id, name, category_id, icon, path, description) values (6, 'Development Process', 2, '', 'development_process', 'The software development life cycle is the process of dividing software development work into distinct phases to improve design, product management, and project management.')
insert into item (id, name, category_id, icon, path, description) values (7, 'Contribution Management', 2, '', 'contribution_management', 'Tracking and managing contributions to software.')
insert into item (id, name, category_id, icon, path, description) values (8, 'Requirements Analysis', 2, '', 'requirements_analysis', 'Statements about what functions a software product should perform, including any constraints under which it should operate but avoiding as much as possible implementation details.')
insert into item (id, name, category_id, icon, path, description) values (9, 'Software Design', 2, '', 'software_design', 'Major considerations in designing software to meet its scientific objectives and sustainability goals. Software design usually follows from requirements specification, and involves problem solving and planning a software solution.')
insert into item (id, name, category_id, icon, path, description) values (10, 'Onboarding', 2, '', 'onboarding', 'The process of integrating a new employee into an organization or a new team member into a team.')
insert into item (id, name, category_id, icon, path, description) values (11, 'Offboarding', 2, '', 'offboarding', 'The process of transferring employee knowledge to the broader team or organization.')
insert into item (id, name, category_id, icon, path, description) values (12, 'Performance Testing', 3, '', 'performance_testing', 'Determining the speed, scalability, reliability, and stability of an application under a variety of workloads.')
insert into item (id, name, category_id, icon, path, description) values (13, 'Performance Regression Testing', 3, '', 'performance_regression_testing', 'Undertaking testing that exercises performance capabilities combined with plans for how to make practical use of that performance data')
insert into item (id, name, category_id, icon, path, description) values (14, 'Performance Portability', 3, '', 'performance_portability', 'Software exhibiting similar performance across multiple platforms, with the time to solution reflecting efficient utilization of computational resources on each platform.')
insert into item (id, name, category_id, icon, path, description) values (15, 'Testing', 4, '', 'testing', 'The process of ensuring that a software product is meeting its requirements at each stage of development, delivery, and deployment. Testing helps to avoid defects and verify correctness.')
insert into item (id, name, category_id, icon, path, description) values (16, 'Reproducibility', 4, '', 'reproducibility', 'Any effort whose goal is to increase trustworthiness and reuse of computational capabilities and results and to ensure correctness.')
insert into item (id, name, category_id, icon, path, description) values (17, 'Virtual Tools', 5, '', 'virtual_tools', 'The process of documenting and sharing team products and artifacts with virtual tools (e.g. Jira, Google Docs) to communicate goals, objectives.')
insert into item (id, name, category_id, icon, path, description) values (18, 'Telecommuting Communication Practices', 5, '', 'telecommuting_communication_practices', 'The use of video and audio conferencing technology for co-located, hybrid (co-located + remote), or distributed teams.')
commit
insert into item_questions (item_id, questions) values (1, 'Not currently being used')
insert into item_questions (item_id, questions) values (1, 'Code is checked in to a revision control system, such as Git')
insert into item_questions (item_id, questions) values (1, 'Developers make use of pull requests and multiple branches, etc.')
insert into item_questions (item_id, questions) values (1, 'Has a documented workflow for merging code for quality assurance and releases')
insert into item_questions (item_id, questions) values (2, 'Not currently being used')
insert into item_questions (item_id, questions) values (2, 'Ad-hoc code reviews')
insert into item_questions (item_id, questions) values (2, 'Regular code reviews (e.g., weekly meetings)')
insert into item_questions (item_id, questions) values (2, 'Documented process for code reviews to be automated in workflow (e.g., via pull requests)')
insert into item_questions (item_id, questions) values (3, 'Not currently being used')
insert into item_questions (item_id, questions) values (3, 'Ad-hoc issue tracking')
insert into item_questions (item_id, questions) values (3, 'Dedicated issue tracking system consistently being used')
insert into item_questions (item_id, questions) values (3, 'Documented process for how issue tracking is done (e.g. Kanban board, assigned responsibilities)')
insert into item_questions (item_id, questions) values (4, 'Not currently being used')
insert into item_questions (item_id, questions) values (4, 'Manual deployment (e.g., using a script or checklist)')
insert into item_questions (item_id, questions) values (4, 'Deployment as part of development workflow with manual intervention')
insert into item_questions (item_id, questions) values (4, 'Continuous deployment (or deployment combined with continuous integration)')
insert into item_questions (item_id, questions) values (5, 'Not currently being used')
insert into item_questions (item_id, questions) values (5, 'Informally maintained or ad-hoc text files')
insert into item_questions (item_id, questions) values (5, 'Code and documentation are cross-referenced and updated when committed to repository')
insert into item_questions (item_id, questions) values (5, 'Integrated with the software release workflow')
insert into item_questions (item_id, questions) values (6, 'Not currently being used')
insert into item_questions (item_id, questions) values (6, 'Has development process but it is based on ad-hoc rules')
insert into item_questions (item_id, questions) values (6, 'Employs a basic iterative development process')
insert into item_questions (item_id, questions) values (6, 'Follows a responsive, flexible development methodology (e.g. Agile)')
insert into item_questions (item_id, questions) values (7, 'Not currently being used')
insert into item_questions (item_id, questions) values (7, 'Guidelines for accepting contributions')
insert into item_questions (item_id, questions) values (7, 'Clearly defined standards for coding and documentation, including community standards')
insert into item_questions (item_id, questions) values (7, 'Provenance and license checking for contributions')
insert into item_questions (item_id, questions) values (8, 'Not currently being used')
insert into item_questions (item_id, questions) values (8, 'Development guidelines include requirements gathering')
insert into item_questions (item_id, questions) values (8, 'Formal requirements gathering is undertaken as part of the project')
insert into item_questions (item_id, questions) values (8, 'Formally tracked and documented through a requirements management system')
insert into item_questions (item_id, questions) values (9, 'Not currently being used')
insert into item_questions (item_id, questions) values (9, 'Development guidelines include design in the process')
insert into item_questions (item_id, questions) values (9, 'A modeling language is employed for key aspects of the project')
insert into item_questions (item_id, questions) values (9, 'Visual modeling using a graphical representation to capture design')
insert into item_questions (item_id, questions) values (10, 'Not currently being used')
insert into item_questions (item_id, questions) values (10, 'Consistent onboarding process is followed')
insert into item_questions (item_id, questions) values (10, 'Minimally documented onboarding process')
insert into item_questions (item_id, questions) values (10, 'Fully documented onboarding process is used for all personnel changes')
insert into item_questions (item_id, questions) values (11, 'Not currently being used')
insert into item_questions (item_id, questions) values (11, 'Consistent offboarding process is followed')
insert into item_questions (item_id, questions) values (11, 'Minimally documented offboarding process')
insert into item_questions (item_id, questions) values (11, 'Fully documented offboarding process is used for all personnel changes')
insert into item_questions (item_id, questions) values (12, 'Not currently being used')
insert into item_questions (item_id, questions) values (12, 'Conducts performance tests')
insert into item_questions (item_id, questions) values (12, 'Automated performance tests are run regularly')
insert into item_questions (item_id, questions) values (12, 'Uses performance profiling tools to guide performance-related improvements')
insert into item_questions (item_id, questions) values (13, 'Not currently being used')
insert into item_questions (item_id, questions) values (13, 'A testable primary use case and set of measurements are established')
insert into item_questions (item_id, questions) values (13, 'The performance history of the tests are tracked over time')
insert into item_questions (item_id, questions) values (13, 'Performance tracking is run and reviewed at regular intervals on relevant platforms')
insert into item_questions (item_id, questions) values (14, 'Not currently being used')
insert into item_questions (item_id, questions) values (14, 'Utilize a standard parallel programming model (e.g. MPI)')
insert into item_questions (item_id, questions) values (14, 'Utilize a programming model designed for portability (e.g. OpenACC, OpenMP)')
insert into item_questions (item_id, questions) values (14, 'Re-writing the application to utilize an advanced portability library (e.g. Kokkos)')
insert into item_questions (item_id, questions) values (15, 'Not currently being used')
insert into item_questions (item_id, questions) values (15, 'Comparison used to create system-level no-change tests')
insert into item_questions (item_id, questions) values (15, 'Unit testing for refactored and new code')
insert into item_questions (item_id, questions) values (15, 'Continuous integration')
insert into item_questions (item_id, questions) values (16, 'Not currently being used')
insert into item_questions (item_id, questions) values (16, 'Publication of code')
insert into item_questions (item_id, questions) values (16, 'Inclusion of data when code is published')
insert into item_questions (item_id, questions) values (16, 'The entire provenance of the application and its execution environment is published')
insert into item_questions (item_id, questions) values (17, 'Not currently being used')
insert into item_questions (item_id, questions) values (17, 'Used occasionally')
insert into item_questions (item_id, questions) values (17, 'Used on a regualar basis in order to improve processes')
insert into item_questions (item_id, questions) values (17, 'Used strategically to facilitate asynchronous and synchronous communication in order to build trust through transparency')
insert into item_questions (item_id, questions) values (18, 'Not currently being used')
insert into item_questions (item_id, questions) values (18, 'Audio conferencing and/or video conferencing tools for audio only or occasional video use')
insert into item_questions (item_id, questions) values (18, 'Use of video conferencing with defined inclusivity guidelines')
insert into item_questions (item_id, questions) values (18, 'Team awareness of inclusive practices in the use of video conferencing combined with utlizing advanced communication features (e.g. voting, sketch pads)')
commit
create sequence hibernate_sequence start with 1 increment by 1
create table activity (id bigint not null, description TEXT, icon varchar(255), name varchar(255), path varchar(255), primary key (id))
create table category (id bigint not null, description TEXT, icon varchar(255), name varchar(255), path varchar(255), activity_id bigint, primary key (id))
create table item (id bigint not null, description TEXT, icon varchar(255), name varchar(255), path varchar(255), score integer, category_id bigint, primary key (id))
create table item_questions (item_id bigint not null, questions varchar(255))
create table score (id bigint not null, boost varchar(255), color varchar(255), name varchar(255), "value" integer, activity_id bigint, primary key (id))
alter table category add constraint FKdgejwxouqepc1gis4eepmtocd foreign key (activity_id) references activity
alter table item add constraint FK2n9w8d0dp4bsfra9dcg0046l4 foreign key (category_id) references category
alter table item_questions add constraint FKghy98omv8lu675kvlbyks8wdo foreign key (item_id) references item
alter table score add constraint FKogwht6qy0n3bdp3qeh0ecna0n foreign key (activity_id) references activity
insert into activity (id, name, icon, path, description) values (1, 'Assessment', 'CLIPBOARD_CHECK', 'assessment', 'The assessment section is used to estimate the degree to which software engineering practices are currently being used by your project.')
insert into activity (id, name, icon, path, description) values (2, 'Tracking', 'TASKS', 'tracking', '** COMING SOON ** The tracking section is used to translate your assessment into progress tracking cards (PTCs) so that you can keep track of improvements to you project''''s practices.')
insert into activity (id, name, icon, path, description) values (3, 'Integration', 'COGS', 'integration', '** COMING SOON ** The integration section is used to aid the integration of progress tracking cards into your development workflow.')
commit
insert into score (id, name, boost, "value", color, activity_id) values (1, 'None', 'Can you do better?', 0, '#000000', 1)
insert into score (id, name, boost, "value", color, activity_id) values (2, 'Basic', 'Good start!', 33, '#DC267F', 1)
insert into score (id, name, boost, "value", color, activity_id) values (3, 'Intermediate', 'Keep up the good work!', 66, '#FFB000', 1)
insert into score (id, name, boost, "value", color, activity_id) values (4, 'Advanced', 'Awesome!', 100, '#066999', 1)
commit
insert into category (id, name, activity_id, icon, path, description) values (1, 'Better Development', 1, '', 'development', 'Improving scientific software development—a process of writing, maintaining, and extending code—in order to increase software productivity, quality, and sustainability, as key aspects of advancing overall scientific productivity.')
insert into category (id, name, activity_id, icon, path, description) values (2, 'Better Planning', 1, '', 'planning', 'Improving strategies for planning in order to increase software productivity, quality, and sustainability, while underscoring the unique characteristics of scientific software, including that requirements often change during discovery.')
insert into category (id, name, activity_id, icon, path, description) values (3, 'Better Performance', 1, '', 'performance', 'Improving strategies for writing scientific software that is efficient, scalable, and portable—from laptops to emerging extreme-scale architectures—while preserving other code quality metrics such as correctness and usability.')
insert into category (id, name, activity_id, icon, path, description) values (4, 'Better Reliability', 1, '', 'reliability', 'Improving methods of testing and verification to ensure that software is robust and produces reliable results, thereby helping to ensure the integrity of research and enable collaboration across teams.')
insert into category (id, name, activity_id, icon, path, description) values (5, 'Better Collaboration', 1, '', 'collaboration', 'Improving ways to facilitate work across teams and to promote partnerships and community contributions via software. We consider projects of all sizes, from small teams through multi-institutional aggregate teams.')
commit
insert into item (id, name, category_id, icon, path, description) values (1, 'Revision Control', 1, '', 'revision_control', 'Approaches for managing changes to files (source code, documentation, data) as well as maintaining their history and attribution, especially across multiple contributors and branches of development (also known as version control).')
insert into item (id, name, category_id, icon, path, description) values (2, 'Code Reviews', 1, '', 'code_reviews', 'The act of consciously and systematically convening with one''s fellow programmers to check each other''s code for mistakes, and has been repeatedly shown to accelerate and streamline the process of software development like few other practices can.')
insert into item (id, name, category_id, icon, path, description) values (3, 'Issue Tracking', 1, '', 'issue_tracking', 'The process of capturing, reporting, tracking, and managing information about issues related to software. Issues include bugs, feature requests, missing documentation, and other problems and/or requirements.')
insert into item (id, name, category_id, icon, path, description) values (4, 'Deployment', 1, '', 'deployment', 'Approaches for versioning, packaging, releasing, and deploying software, documentation, or data for users to then obtain, install, and use.')
insert into item (id, name, category_id, icon, path, description) values (5, 'Documentation', 1, '', 'documentation', 'Creating, maintaining, and hosting quality documentation (written text or illustration) about the use, operation, or design of software.')
insert into item (id, name, category_id, icon, path, description) values (6, 'Development Process', 2, '', 'development_process', 'The software development life cycle is the process of dividing software development work into distinct phases to improve design, product management, and project management.')
insert into item (id, name, category_id, icon, path, description) values (7, 'Contribution Management', 2, '', 'contribution_management', 'Tracking and managing contributions to software.')
insert into item (id, name, category_id, icon, path, description) values (8, 'Requirements Analysis', 2, '', 'requirements_analysis', 'Statements about what functions a software product should perform, including any constraints under which it should operate but avoiding as much as possible implementation details.')
insert into item (id, name, category_id, icon, path, description) values (9, 'Software Design', 2, '', 'software_design', 'Major considerations in designing software to meet its scientific objectives and sustainability goals. Software design usually follows from requirements specification, and involves problem solving and planning a software solution.')
insert into item (id, name, category_id, icon, path, description) values (10, 'Onboarding', 2, '', 'onboarding', 'The process of integrating a new employee into an organization or a new team member into a team.')
insert into item (id, name, category_id, icon, path, description) values (11, 'Offboarding', 2, '', 'offboarding', 'The process of transferring employee knowledge to the broader team or organization.')
insert into item (id, name, category_id, icon, path, description) values (12, 'Performance Testing', 3, '', 'performance_testing', 'Determining the speed, scalability, reliability, and stability of an application under a variety of workloads.')
insert into item (id, name, category_id, icon, path, description) values (13, 'Performance Regression Testing', 3, '', 'performance_regression_testing', 'Undertaking testing that exercises performance capabilities combined with plans for how to make practical use of that performance data')
insert into item (id, name, category_id, icon, path, description) values (14, 'Performance Portability', 3, '', 'performance_portability', 'Software exhibiting similar performance across multiple platforms, with the time to solution reflecting efficient utilization of computational resources on each platform.')
insert into item (id, name, category_id, icon, path, description) values (15, 'Testing', 4, '', 'testing', 'The process of ensuring that a software product is meeting its requirements at each stage of development, delivery, and deployment. Testing helps to avoid defects and verify correctness.')
insert into item (id, name, category_id, icon, path, description) values (16, 'Reproducibility', 4, '', 'reproducibility', 'Any effort whose goal is to increase trustworthiness and reuse of computational capabilities and results and to ensure correctness.')
insert into item (id, name, category_id, icon, path, description) values (17, 'Virtual Tools', 5, '', 'virtual_tools', 'The process of documenting and sharing team products and artifacts with virtual tools (e.g. Jira, Google Docs) to communicate goals, objectives.')
insert into item (id, name, category_id, icon, path, description) values (18, 'Telecommuting Communication Practices', 5, '', 'telecommuting_communication_practices', 'The use of video and audio conferencing technology for co-located, hybrid (co-located + remote), or distributed teams.')
commit
insert into item_questions (item_id, questions) values (1, 'Not currently being used')
insert into item_questions (item_id, questions) values (1, 'Code is checked in to a revision control system, such as Git')
insert into item_questions (item_id, questions) values (1, 'Developers make use of pull requests and multiple branches, etc.')
insert into item_questions (item_id, questions) values (1, 'Has a documented workflow for merging code for quality assurance and releases')
insert into item_questions (item_id, questions) values (2, 'Not currently being used')
insert into item_questions (item_id, questions) values (2, 'Ad-hoc code reviews')
insert into item_questions (item_id, questions) values (2, 'Regular code reviews (e.g., weekly meetings)')
insert into item_questions (item_id, questions) values (2, 'Documented process for code reviews to be automated in workflow (e.g., via pull requests)')
insert into item_questions (item_id, questions) values (3, 'Not currently being used')
insert into item_questions (item_id, questions) values (3, 'Ad-hoc issue tracking')
insert into item_questions (item_id, questions) values (3, 'Dedicated issue tracking system consistently being used')
insert into item_questions (item_id, questions) values (3, 'Documented process for how issue tracking is done (e.g. Kanban board, assigned responsibilities)')
insert into item_questions (item_id, questions) values (4, 'Not currently being used')
insert into item_questions (item_id, questions) values (4, 'Manual deployment (e.g., using a script or checklist)')
insert into item_questions (item_id, questions) values (4, 'Deployment as part of development workflow with manual intervention')
insert into item_questions (item_id, questions) values (4, 'Continuous deployment (or deployment combined with continuous integration)')
insert into item_questions (item_id, questions) values (5, 'Not currently being used')
insert into item_questions (item_id, questions) values (5, 'Informally maintained or ad-hoc text files')
insert into item_questions (item_id, questions) values (5, 'Code and documentation are cross-referenced and updated when committed to repository')
insert into item_questions (item_id, questions) values (5, 'Integrated with the software release workflow')
insert into item_questions (item_id, questions) values (6, 'Not currently being used')
insert into item_questions (item_id, questions) values (6, 'Has development process but it is based on ad-hoc rules')
insert into item_questions (item_id, questions) values (6, 'Employs a basic iterative development process')
insert into item_questions (item_id, questions) values (6, 'Follows a responsive, flexible development methodology (e.g. Agile)')
insert into item_questions (item_id, questions) values (7, 'Not currently being used')
insert into item_questions (item_id, questions) values (7, 'Guidelines for accepting contributions')
insert into item_questions (item_id, questions) values (7, 'Clearly defined standards for coding and documentation, including community standards')
insert into item_questions (item_id, questions) values (7, 'Provenance and license checking for contributions')
insert into item_questions (item_id, questions) values (8, 'Not currently being used')
insert into item_questions (item_id, questions) values (8, 'Development guidelines include requirements gathering')
insert into item_questions (item_id, questions) values (8, 'Formal requirements gathering is undertaken as part of the project')
insert into item_questions (item_id, questions) values (8, 'Formally tracked and documented through a requirements management system')
insert into item_questions (item_id, questions) values (9, 'Not currently being used')
insert into item_questions (item_id, questions) values (9, 'Development guidelines include design in the process')
insert into item_questions (item_id, questions) values (9, 'A modeling language is employed for key aspects of the project')
insert into item_questions (item_id, questions) values (9, 'Visual modeling using a graphical representation to capture design')
insert into item_questions (item_id, questions) values (10, 'Not currently being used')
insert into item_questions (item_id, questions) values (10, 'Consistent onboarding process is followed')
insert into item_questions (item_id, questions) values (10, 'Minimally documented onboarding process')
insert into item_questions (item_id, questions) values (10, 'Fully documented onboarding process is used for all personnel changes')
insert into item_questions (item_id, questions) values (11, 'Not currently being used')
insert into item_questions (item_id, questions) values (11, 'Consistent offboarding process is followed')
insert into item_questions (item_id, questions) values (11, 'Minimally documented offboarding process')
insert into item_questions (item_id, questions) values (11, 'Fully documented offboarding process is used for all personnel changes')
insert into item_questions (item_id, questions) values (12, 'Not currently being used')
insert into item_questions (item_id, questions) values (12, 'Conducts performance tests')
insert into item_questions (item_id, questions) values (12, 'Automated performance tests are run regularly')
insert into item_questions (item_id, questions) values (12, 'Uses performance profiling tools to guide performance-related improvements')
insert into item_questions (item_id, questions) values (13, 'Not currently being used')
insert into item_questions (item_id, questions) values (13, 'A testable primary use case and set of measurements are established')
insert into item_questions (item_id, questions) values (13, 'The performance history of the tests are tracked over time')
insert into item_questions (item_id, questions) values (13, 'Performance tracking is run and reviewed at regular intervals on relevant platforms')
insert into item_questions (item_id, questions) values (14, 'Not currently being used')
insert into item_questions (item_id, questions) values (14, 'Utilize a standard parallel programming model (e.g. MPI)')
insert into item_questions (item_id, questions) values (14, 'Utilize a programming model designed for portability (e.g. OpenACC, OpenMP)')
insert into item_questions (item_id, questions) values (14, 'Re-writing the application to utilize an advanced portability library (e.g. Kokkos)')
insert into item_questions (item_id, questions) values (15, 'Not currently being used')
insert into item_questions (item_id, questions) values (15, 'Comparison used to create system-level no-change tests')
insert into item_questions (item_id, questions) values (15, 'Unit testing for refactored and new code')
insert into item_questions (item_id, questions) values (15, 'Continuous integration')
insert into item_questions (item_id, questions) values (16, 'Not currently being used')
insert into item_questions (item_id, questions) values (16, 'Publication of code')
insert into item_questions (item_id, questions) values (16, 'Inclusion of data when code is published')
insert into item_questions (item_id, questions) values (16, 'The entire provenance of the application and its execution environment is published')
insert into item_questions (item_id, questions) values (17, 'Not currently being used')
insert into item_questions (item_id, questions) values (17, 'Used occasionally')
insert into item_questions (item_id, questions) values (17, 'Used on a regualar basis in order to improve processes')
insert into item_questions (item_id, questions) values (17, 'Used strategically to facilitate asynchronous and synchronous communication in order to build trust through transparency')
insert into item_questions (item_id, questions) values (18, 'Not currently being used')
insert into item_questions (item_id, questions) values (18, 'Audio conferencing and/or video conferencing tools for audio only or occasional video use')
insert into item_questions (item_id, questions) values (18, 'Use of video conferencing with defined inclusivity guidelines')
insert into item_questions (item_id, questions) values (18, 'Team awareness of inclusive practices in the use of video conferencing combined with utlizing advanced communication features (e.g. voting, sketch pads)')
commit
create sequence hibernate_sequence start with 1 increment by 1
create table activity (id bigint not null, description TEXT, icon varchar(255), name varchar(255), path varchar(255), primary key (id))
create table category (id bigint not null, description TEXT, icon varchar(255), name varchar(255), path varchar(255), activity_id bigint, primary key (id))
create table item (id bigint not null, description TEXT, icon varchar(255), name varchar(255), path varchar(255), score integer, category_id bigint, primary key (id))
create table item_questions (item_id bigint not null, questions varchar(255))
create table score (id bigint not null, boost varchar(255), color varchar(255), name varchar(255), "value" integer, activity_id bigint, primary key (id))
alter table category add constraint FKdgejwxouqepc1gis4eepmtocd foreign key (activity_id) references activity
alter table item add constraint FK2n9w8d0dp4bsfra9dcg0046l4 foreign key (category_id) references category
alter table item_questions add constraint FKghy98omv8lu675kvlbyks8wdo foreign key (item_id) references item
alter table score add constraint FKogwht6qy0n3bdp3qeh0ecna0n foreign key (activity_id) references activity
insert into activity (id, name, icon, path, description) values (1, 'Assessment', 'CLIPBOARD_CHECK', 'assessment', 'The assessment section is used to estimate the degree to which software engineering practices are currently being used by your project.')
insert into activity (id, name, icon, path, description) values (2, 'Tracking', 'TASKS', 'tracking', '** COMING SOON ** The tracking section is used to translate your assessment into progress tracking cards (PTCs) so that you can keep track of improvements to you project''''s practices.')
insert into activity (id, name, icon, path, description) values (3, 'Integration', 'COGS', 'integration', '** COMING SOON ** The integration section is used to aid the integration of progress tracking cards into your development workflow.')
commit
insert into score (id, name, boost, "value", color, activity_id) values (1, 'None', 'Can you do better?', 0, '#000000', 1)
insert into score (id, name, boost, "value", color, activity_id) values (2, 'Basic', 'Good start!', 33, '#DC267F', 1)
insert into score (id, name, boost, "value", color, activity_id) values (3, 'Intermediate', 'Keep up the good work!', 66, '#FFB000', 1)
insert into score (id, name, boost, "value", color, activity_id) values (4, 'Advanced', 'Awesome!', 100, '#066999', 1)
commit
insert into category (id, name, activity_id, icon, path, description) values (1, 'Better Development', 1, '', 'development', 'Improving scientific software development—a process of writing, maintaining, and extending code—in order to increase software productivity, quality, and sustainability, as key aspects of advancing overall scientific productivity.')
insert into category (id, name, activity_id, icon, path, description) values (2, 'Better Planning', 1, '', 'planning', 'Improving strategies for planning in order to increase software productivity, quality, and sustainability, while underscoring the unique characteristics of scientific software, including that requirements often change during discovery.')
insert into category (id, name, activity_id, icon, path, description) values (3, 'Better Performance', 1, '', 'performance', 'Improving strategies for writing scientific software that is efficient, scalable, and portable—from laptops to emerging extreme-scale architectures—while preserving other code quality metrics such as correctness and usability.')
insert into category (id, name, activity_id, icon, path, description) values (4, 'Better Reliability', 1, '', 'reliability', 'Improving methods of testing and verification to ensure that software is robust and produces reliable results, thereby helping to ensure the integrity of research and enable collaboration across teams.')
insert into category (id, name, activity_id, icon, path, description) values (5, 'Better Collaboration', 1, '', 'collaboration', 'Improving ways to facilitate work across teams and to promote partnerships and community contributions via software. We consider projects of all sizes, from small teams through multi-institutional aggregate teams.')
commit
insert into item (id, name, category_id, icon, path, description) values (1, 'Revision Control', 1, '', 'revision_control', 'Approaches for managing changes to files (source code, documentation, data) as well as maintaining their history and attribution, especially across multiple contributors and branches of development (also known as version control).')
insert into item (id, name, category_id, icon, path, description) values (2, 'Code Reviews', 1, '', 'code_reviews', 'The act of consciously and systematically convening with one''s fellow programmers to check each other''s code for mistakes, and has been repeatedly shown to accelerate and streamline the process of software development like few other practices can.')
insert into item (id, name, category_id, icon, path, description) values (3, 'Issue Tracking', 1, '', 'issue_tracking', 'The process of capturing, reporting, tracking, and managing information about issues related to software. Issues include bugs, feature requests, missing documentation, and other problems and/or requirements.')
insert into item (id, name, category_id, icon, path, description) values (4, 'Deployment', 1, '', 'deployment', 'Approaches for versioning, packaging, releasing, and deploying software, documentation, or data for users to then obtain, install, and use.')
insert into item (id, name, category_id, icon, path, description) values (5, 'Documentation', 1, '', 'documentation', 'Creating, maintaining, and hosting quality documentation (written text or illustration) about the use, operation, or design of software.')
insert into item (id, name, category_id, icon, path, description) values (6, 'Development Process', 2, '', 'development_process', 'The software development life cycle is the process of dividing software development work into distinct phases to improve design, product management, and project management.')
insert into item (id, name, category_id, icon, path, description) values (7, 'Contribution Management', 2, '', 'contribution_management', 'Tracking and managing contributions to software.')
insert into item (id, name, category_id, icon, path, description) values (8, 'Requirements Analysis', 2, '', 'requirements_analysis', 'Statements about what functions a software product should perform, including any constraints under which it should operate but avoiding as much as possible implementation details.')
insert into item (id, name, category_id, icon, path, description) values (9, 'Software Design', 2, '', 'software_design', 'Major considerations in designing software to meet its scientific objectives and sustainability goals. Software design usually follows from requirements specification, and involves problem solving and planning a software solution.')
insert into item (id, name, category_id, icon, path, description) values (10, 'Onboarding', 2, '', 'onboarding', 'The process of integrating a new employee into an organization or a new team member into a team.')
insert into item (id, name, category_id, icon, path, description) values (11, 'Offboarding', 2, '', 'offboarding', 'The process of transferring employee knowledge to the broader team or organization.')
insert into item (id, name, category_id, icon, path, description) values (12, 'Performance Testing', 3, '', 'performance_testing', 'Determining the speed, scalability, reliability, and stability of an application under a variety of workloads.')
insert into item (id, name, category_id, icon, path, description) values (13, 'Performance Regression Testing', 3, '', 'performance_regression_testing', 'Undertaking testing that exercises performance capabilities combined with plans for how to make practical use of that performance data')
insert into item (id, name, category_id, icon, path, description) values (14, 'Performance Portability', 3, '', 'performance_portability', 'Software exhibiting similar performance across multiple platforms, with the time to solution reflecting efficient utilization of computational resources on each platform.')
insert into item (id, name, category_id, icon, path, description) values (15, 'Testing', 4, '', 'testing', 'The process of ensuring that a software product is meeting its requirements at each stage of development, delivery, and deployment. Testing helps to avoid defects and verify correctness.')
insert into item (id, name, category_id, icon, path, description) values (16, 'Reproducibility', 4, '', 'reproducibility', 'Any effort whose goal is to increase trustworthiness and reuse of computational capabilities and results and to ensure correctness.')
insert into item (id, name, category_id, icon, path, description) values (17, 'Virtual Tools', 5, '', 'virtual_tools', 'The process of documenting and sharing team products and artifacts with virtual tools (e.g. Jira, Google Docs) to communicate goals, objectives.')
insert into item (id, name, category_id, icon, path, description) values (18, 'Telecommuting Communication Practices', 5, '', 'telecommuting_communication_practices', 'The use of video and audio conferencing technology for co-located, hybrid (co-located + remote), or distributed teams.')
commit
insert into item_questions (item_id, questions) values (1, 'Not currently being used')
insert into item_questions (item_id, questions) values (1, 'Code is checked in to a revision control system, such as Git')
insert into item_questions (item_id, questions) values (1, 'Developers make use of pull requests and multiple branches, etc.')
insert into item_questions (item_id, questions) values (1, 'Has a documented workflow for merging code for quality assurance and releases')
insert into item_questions (item_id, questions) values (2, 'Not currently being used')
insert into item_questions (item_id, questions) values (2, 'Ad-hoc code reviews')
insert into item_questions (item_id, questions) values (2, 'Regular code reviews (e.g., weekly meetings)')
insert into item_questions (item_id, questions) values (2, 'Documented process for code reviews to be automated in workflow (e.g., via pull requests)')
insert into item_questions (item_id, questions) values (3, 'Not currently being used')
insert into item_questions (item_id, questions) values (3, 'Ad-hoc issue tracking')
insert into item_questions (item_id, questions) values (3, 'Dedicated issue tracking system consistently being used')
insert into item_questions (item_id, questions) values (3, 'Documented process for how issue tracking is done (e.g. Kanban board, assigned responsibilities)')
insert into item_questions (item_id, questions) values (4, 'Not currently being used')
insert into item_questions (item_id, questions) values (4, 'Manual deployment (e.g., using a script or checklist)')
insert into item_questions (item_id, questions) values (4, 'Deployment as part of development workflow with manual intervention')
insert into item_questions (item_id, questions) values (4, 'Continuous deployment (or deployment combined with continuous integration)')
insert into item_questions (item_id, questions) values (5, 'Not currently being used')
insert into item_questions (item_id, questions) values (5, 'Informally maintained or ad-hoc text files')
insert into item_questions (item_id, questions) values (5, 'Code and documentation are cross-referenced and updated when committed to repository')
insert into item_questions (item_id, questions) values (5, 'Integrated with the software release workflow')
insert into item_questions (item_id, questions) values (6, 'Not currently being used')
insert into item_questions (item_id, questions) values (6, 'Has development process but it is based on ad-hoc rules')
insert into item_questions (item_id, questions) values (6, 'Employs a basic iterative development process')
insert into item_questions (item_id, questions) values (6, 'Follows a responsive, flexible development methodology (e.g. Agile)')
insert into item_questions (item_id, questions) values (7, 'Not currently being used')
insert into item_questions (item_id, questions) values (7, 'Guidelines for accepting contributions')
insert into item_questions (item_id, questions) values (7, 'Clearly defined standards for coding and documentation, including community standards')
insert into item_questions (item_id, questions) values (7, 'Provenance and license checking for contributions')
insert into item_questions (item_id, questions) values (8, 'Not currently being used')
insert into item_questions (item_id, questions) values (8, 'Development guidelines include requirements gathering')
insert into item_questions (item_id, questions) values (8, 'Formal requirements gathering is undertaken as part of the project')
insert into item_questions (item_id, questions) values (8, 'Formally tracked and documented through a requirements management system')
insert into item_questions (item_id, questions) values (9, 'Not currently being used')
insert into item_questions (item_id, questions) values (9, 'Development guidelines include design in the process')
insert into item_questions (item_id, questions) values (9, 'A modeling language is employed for key aspects of the project')
insert into item_questions (item_id, questions) values (9, 'Visual modeling using a graphical representation to capture design')
insert into item_questions (item_id, questions) values (10, 'Not currently being used')
insert into item_questions (item_id, questions) values (10, 'Consistent onboarding process is followed')
insert into item_questions (item_id, questions) values (10, 'Minimally documented onboarding process')
insert into item_questions (item_id, questions) values (10, 'Fully documented onboarding process is used for all personnel changes')
insert into item_questions (item_id, questions) values (11, 'Not currently being used')
insert into item_questions (item_id, questions) values (11, 'Consistent offboarding process is followed')
insert into item_questions (item_id, questions) values (11, 'Minimally documented offboarding process')
insert into item_questions (item_id, questions) values (11, 'Fully documented offboarding process is used for all personnel changes')
insert into item_questions (item_id, questions) values (12, 'Not currently being used')
insert into item_questions (item_id, questions) values (12, 'Conducts performance tests')
insert into item_questions (item_id, questions) values (12, 'Automated performance tests are run regularly')
insert into item_questions (item_id, questions) values (12, 'Uses performance profiling tools to guide performance-related improvements')
insert into item_questions (item_id, questions) values (13, 'Not currently being used')
insert into item_questions (item_id, questions) values (13, 'A testable primary use case and set of measurements are established')
insert into item_questions (item_id, questions) values (13, 'The performance history of the tests are tracked over time')
insert into item_questions (item_id, questions) values (13, 'Performance tracking is run and reviewed at regular intervals on relevant platforms')
insert into item_questions (item_id, questions) values (14, 'Not currently being used')
insert into item_questions (item_id, questions) values (14, 'Utilize a standard parallel programming model (e.g. MPI)')
insert into item_questions (item_id, questions) values (14, 'Utilize a programming model designed for portability (e.g. OpenACC, OpenMP)')
insert into item_questions (item_id, questions) values (14, 'Re-writing the application to utilize an advanced portability library (e.g. Kokkos)')
insert into item_questions (item_id, questions) values (15, 'Not currently being used')
insert into item_questions (item_id, questions) values (15, 'Comparison used to create system-level no-change tests')
insert into item_questions (item_id, questions) values (15, 'Unit testing for refactored and new code')
insert into item_questions (item_id, questions) values (15, 'Continuous integration')
insert into item_questions (item_id, questions) values (16, 'Not currently being used')
insert into item_questions (item_id, questions) values (16, 'Publication of code')
insert into item_questions (item_id, questions) values (16, 'Inclusion of data when code is published')
insert into item_questions (item_id, questions) values (16, 'The entire provenance of the application and its execution environment is published')
insert into item_questions (item_id, questions) values (17, 'Not currently being used')
insert into item_questions (item_id, questions) values (17, 'Used occasionally')
insert into item_questions (item_id, questions) values (17, 'Used on a regualar basis in order to improve processes')
insert into item_questions (item_id, questions) values (17, 'Used strategically to facilitate asynchronous and synchronous communication in order to build trust through transparency')
insert into item_questions (item_id, questions) values (18, 'Not currently being used')
insert into item_questions (item_id, questions) values (18, 'Audio conferencing and/or video conferencing tools for audio only or occasional video use')
insert into item_questions (item_id, questions) values (18, 'Use of video conferencing with defined inclusivity guidelines')
insert into item_questions (item_id, questions) values (18, 'Team awareness of inclusive practices in the use of video conferencing combined with utlizing advanced communication features (e.g. voting, sketch pads)')
commit
create sequence hibernate_sequence start with 1 increment by 1
create table activity (id bigint not null, description TEXT, icon varchar(255), name varchar(255), path varchar(255), primary key (id))
create table category (id bigint not null, description TEXT, icon varchar(255), name varchar(255), path varchar(255), activity_id bigint, primary key (id))
create table item (id bigint not null, description TEXT, icon varchar(255), name varchar(255), path varchar(255), score integer, category_id bigint, primary key (id))
create table item_questions (item_id bigint not null, questions varchar(255))
create table score (id bigint not null, boost varchar(255), color varchar(255), name varchar(255), "value" integer, activity_id bigint, primary key (id))
alter table category add constraint FKdgejwxouqepc1gis4eepmtocd foreign key (activity_id) references activity
alter table item add constraint FK2n9w8d0dp4bsfra9dcg0046l4 foreign key (category_id) references category
alter table item_questions add constraint FKghy98omv8lu675kvlbyks8wdo foreign key (item_id) references item
alter table score add constraint FKogwht6qy0n3bdp3qeh0ecna0n foreign key (activity_id) references activity
insert into activity (id, name, icon, path, description) values (1, 'Assessment', 'CLIPBOARD_CHECK', 'assessment', 'The assessment section is used to estimate the degree to which software engineering practices are currently being used by your project.')
insert into activity (id, name, icon, path, description) values (2, 'Tracking', 'TASKS', 'tracking', '** COMING SOON ** The tracking section is used to translate your assessment into progress tracking cards (PTCs) so that you can keep track of improvements to you project''''s practices.')
insert into activity (id, name, icon, path, description) values (3, 'Integration', 'COGS', 'integration', '** COMING SOON ** The integration section is used to aid the integration of progress tracking cards into your development workflow.')
commit
insert into score (id, name, boost, "value", color, activity_id) values (1, 'None', 'Can you do better?', 0, '#000000', 1)
insert into score (id, name, boost, "value", color, activity_id) values (2, 'Basic', 'Good start!', 33, '#DC267F', 1)
insert into score (id, name, boost, "value", color, activity_id) values (3, 'Intermediate', 'Keep up the good work!', 66, '#FFB000', 1)
insert into score (id, name, boost, "value", color, activity_id) values (4, 'Advanced', 'Awesome!', 100, '#066999', 1)
commit
insert into category (id, name, activity_id, icon, path, description) values (1, 'Better Development', 1, '', 'development', 'Improving scientific software development—a process of writing, maintaining, and extending code—in order to increase software productivity, quality, and sustainability, as key aspects of advancing overall scientific productivity.')
insert into category (id, name, activity_id, icon, path, description) values (2, 'Better Planning', 1, '', 'planning', 'Improving strategies for planning in order to increase software productivity, quality, and sustainability, while underscoring the unique characteristics of scientific software, including that requirements often change during discovery.')
insert into category (id, name, activity_id, icon, path, description) values (3, 'Better Performance', 1, '', 'performance', 'Improving strategies for writing scientific software that is efficient, scalable, and portable—from laptops to emerging extreme-scale architectures—while preserving other code quality metrics such as correctness and usability.')
insert into category (id, name, activity_id, icon, path, description) values (4, 'Better Reliability', 1, '', 'reliability', 'Improving methods of testing and verification to ensure that software is robust and produces reliable results, thereby helping to ensure the integrity of research and enable collaboration across teams.')
insert into category (id, name, activity_id, icon, path, description) values (5, 'Better Collaboration', 1, '', 'collaboration', 'Improving ways to facilitate work across teams and to promote partnerships and community contributions via software. We consider projects of all sizes, from small teams through multi-institutional aggregate teams.')
commit
insert into item (id, name, category_id, icon, path, description) values (1, 'Revision Control', 1, '', 'revision_control', 'Approaches for managing changes to files (source code, documentation, data) as well as maintaining their history and attribution, especially across multiple contributors and branches of development (also known as version control).')
insert into item (id, name, category_id, icon, path, description) values (2, 'Code Reviews', 1, '', 'code_reviews', 'The act of consciously and systematically convening with one''s fellow programmers to check each other''s code for mistakes, and has been repeatedly shown to accelerate and streamline the process of software development like few other practices can.')
insert into item (id, name, category_id, icon, path, description) values (3, 'Issue Tracking', 1, '', 'issue_tracking', 'The process of capturing, reporting, tracking, and managing information about issues related to software. Issues include bugs, feature requests, missing documentation, and other problems and/or requirements.')
insert into item (id, name, category_id, icon, path, description) values (4, 'Deployment', 1, '', 'deployment', 'Approaches for versioning, packaging, releasing, and deploying software, documentation, or data for users to then obtain, install, and use.')
insert into item (id, name, category_id, icon, path, description) values (5, 'Documentation', 1, '', 'documentation', 'Creating, maintaining, and hosting quality documentation (written text or illustration) about the use, operation, or design of software.')
insert into item (id, name, category_id, icon, path, description) values (6, 'Development Process', 2, '', 'development_process', 'The software development life cycle is the process of dividing software development work into distinct phases to improve design, product management, and project management.')
insert into item (id, name, category_id, icon, path, description) values (7, 'Contribution Management', 2, '', 'contribution_management', 'Tracking and managing contributions to software.')
insert into item (id, name, category_id, icon, path, description) values (8, 'Requirements Analysis', 2, '', 'requirements_analysis', 'Statements about what functions a software product should perform, including any constraints under which it should operate but avoiding as much as possible implementation details.')
insert into item (id, name, category_id, icon, path, description) values (9, 'Software Design', 2, '', 'software_design', 'Major considerations in designing software to meet its scientific objectives and sustainability goals. Software design usually follows from requirements specification, and involves problem solving and planning a software solution.')
insert into item (id, name, category_id, icon, path, description) values (10, 'Onboarding', 2, '', 'onboarding', 'The process of integrating a new employee into an organization or a new team member into a team.')
insert into item (id, name, category_id, icon, path, description) values (11, 'Offboarding', 2, '', 'offboarding', 'The process of transferring employee knowledge to the broader team or organization.')
insert into item (id, name, category_id, icon, path, description) values (12, 'Performance Testing', 3, '', 'performance_testing', 'Determining the speed, scalability, reliability, and stability of an application under a variety of workloads.')
insert into item (id, name, category_id, icon, path, description) values (13, 'Performance Regression Testing', 3, '', 'performance_regression_testing', 'Undertaking testing that exercises performance capabilities combined with plans for how to make practical use of that performance data')
insert into item (id, name, category_id, icon, path, description) values (14, 'Performance Portability', 3, '', 'performance_portability', 'Software exhibiting similar performance across multiple platforms, with the time to solution reflecting efficient utilization of computational resources on each platform.')
insert into item (id, name, category_id, icon, path, description) values (15, 'Testing', 4, '', 'testing', 'The process of ensuring that a software product is meeting its requirements at each stage of development, delivery, and deployment. Testing helps to avoid defects and verify correctness.')
insert into item (id, name, category_id, icon, path, description) values (16, 'Reproducibility', 4, '', 'reproducibility', 'Any effort whose goal is to increase trustworthiness and reuse of computational capabilities and results and to ensure correctness.')
insert into item (id, name, category_id, icon, path, description) values (17, 'Virtual Tools', 5, '', 'virtual_tools', 'The process of documenting and sharing team products and artifacts with virtual tools (e.g. Jira, Google Docs) to communicate goals, objectives.')
insert into item (id, name, category_id, icon, path, description) values (18, 'Telecommuting Communication Practices', 5, '', 'telecommuting_communication_practices', 'The use of video and audio conferencing technology for co-located, hybrid (co-located + remote), or distributed teams.')
commit
insert into item_questions (item_id, questions) values (1, 'Not currently being used')
insert into item_questions (item_id, questions) values (1, 'Code is checked in to a revision control system, such as Git')
insert into item_questions (item_id, questions) values (1, 'Developers make use of pull requests and multiple branches, etc.')
insert into item_questions (item_id, questions) values (1, 'Has a documented workflow for merging code for quality assurance and releases')
insert into item_questions (item_id, questions) values (2, 'Not currently being used')
insert into item_questions (item_id, questions) values (2, 'Ad-hoc code reviews')
insert into item_questions (item_id, questions) values (2, 'Regular code reviews (e.g., weekly meetings)')
insert into item_questions (item_id, questions) values (2, 'Documented process for code reviews to be automated in workflow (e.g., via pull requests)')
insert into item_questions (item_id, questions) values (3, 'Not currently being used')
insert into item_questions (item_id, questions) values (3, 'Ad-hoc issue tracking')
insert into item_questions (item_id, questions) values (3, 'Dedicated issue tracking system consistently being used')
insert into item_questions (item_id, questions) values (3, 'Documented process for how issue tracking is done (e.g. Kanban board, assigned responsibilities)')
insert into item_questions (item_id, questions) values (4, 'Not currently being used')
insert into item_questions (item_id, questions) values (4, 'Manual deployment (e.g., using a script or checklist)')
insert into item_questions (item_id, questions) values (4, 'Deployment as part of development workflow with manual intervention')
insert into item_questions (item_id, questions) values (4, 'Continuous deployment (or deployment combined with continuous integration)')
insert into item_questions (item_id, questions) values (5, 'Not currently being used')
insert into item_questions (item_id, questions) values (5, 'Informally maintained or ad-hoc text files')
insert into item_questions (item_id, questions) values (5, 'Code and documentation are cross-referenced and updated when committed to repository')
insert into item_questions (item_id, questions) values (5, 'Integrated with the software release workflow')
insert into item_questions (item_id, questions) values (6, 'Not currently being used')
insert into item_questions (item_id, questions) values (6, 'Has development process but it is based on ad-hoc rules')
insert into item_questions (item_id, questions) values (6, 'Employs a basic iterative development process')
insert into item_questions (item_id, questions) values (6, 'Follows a responsive, flexible development methodology (e.g. Agile)')
insert into item_questions (item_id, questions) values (7, 'Not currently being used')
insert into item_questions (item_id, questions) values (7, 'Guidelines for accepting contributions')
insert into item_questions (item_id, questions) values (7, 'Clearly defined standards for coding and documentation, including community standards')
insert into item_questions (item_id, questions) values (7, 'Provenance and license checking for contributions')
insert into item_questions (item_id, questions) values (8, 'Not currently being used')
insert into item_questions (item_id, questions) values (8, 'Development guidelines include requirements gathering')
insert into item_questions (item_id, questions) values (8, 'Formal requirements gathering is undertaken as part of the project')
insert into item_questions (item_id, questions) values (8, 'Formally tracked and documented through a requirements management system')
insert into item_questions (item_id, questions) values (9, 'Not currently being used')
insert into item_questions (item_id, questions) values (9, 'Development guidelines include design in the process')
insert into item_questions (item_id, questions) values (9, 'A modeling language is employed for key aspects of the project')
insert into item_questions (item_id, questions) values (9, 'Visual modeling using a graphical representation to capture design')
insert into item_questions (item_id, questions) values (10, 'Not currently being used')
insert into item_questions (item_id, questions) values (10, 'Consistent onboarding process is followed')
insert into item_questions (item_id, questions) values (10, 'Minimally documented onboarding process')
insert into item_questions (item_id, questions) values (10, 'Fully documented onboarding process is used for all personnel changes')
insert into item_questions (item_id, questions) values (11, 'Not currently being used')
insert into item_questions (item_id, questions) values (11, 'Consistent offboarding process is followed')
insert into item_questions (item_id, questions) values (11, 'Minimally documented offboarding process')
insert into item_questions (item_id, questions) values (11, 'Fully documented offboarding process is used for all personnel changes')
insert into item_questions (item_id, questions) values (12, 'Not currently being used')
insert into item_questions (item_id, questions) values (12, 'Conducts performance tests')
insert into item_questions (item_id, questions) values (12, 'Automated performance tests are run regularly')
insert into item_questions (item_id, questions) values (12, 'Uses performance profiling tools to guide performance-related improvements')
insert into item_questions (item_id, questions) values (13, 'Not currently being used')
insert into item_questions (item_id, questions) values (13, 'A testable primary use case and set of measurements are established')
insert into item_questions (item_id, questions) values (13, 'The performance history of the tests are tracked over time')
insert into item_questions (item_id, questions) values (13, 'Performance tracking is run and reviewed at regular intervals on relevant platforms')
insert into item_questions (item_id, questions) values (14, 'Not currently being used')
insert into item_questions (item_id, questions) values (14, 'Utilize a standard parallel programming model (e.g. MPI)')
insert into item_questions (item_id, questions) values (14, 'Utilize a programming model designed for portability (e.g. OpenACC, OpenMP)')
insert into item_questions (item_id, questions) values (14, 'Re-writing the application to utilize an advanced portability library (e.g. Kokkos)')
insert into item_questions (item_id, questions) values (15, 'Not currently being used')
insert into item_questions (item_id, questions) values (15, 'Comparison used to create system-level no-change tests')
insert into item_questions (item_id, questions) values (15, 'Unit testing for refactored and new code')
insert into item_questions (item_id, questions) values (15, 'Continuous integration')
insert into item_questions (item_id, questions) values (16, 'Not currently being used')
insert into item_questions (item_id, questions) values (16, 'Publication of code')
insert into item_questions (item_id, questions) values (16, 'Inclusion of data when code is published')
insert into item_questions (item_id, questions) values (16, 'The entire provenance of the application and its execution environment is published')
insert into item_questions (item_id, questions) values (17, 'Not currently being used')
insert into item_questions (item_id, questions) values (17, 'Used occasionally')
insert into item_questions (item_id, questions) values (17, 'Used on a regualar basis in order to improve processes')
insert into item_questions (item_id, questions) values (17, 'Used strategically to facilitate asynchronous and synchronous communication in order to build trust through transparency')
insert into item_questions (item_id, questions) values (18, 'Not currently being used')
insert into item_questions (item_id, questions) values (18, 'Audio conferencing and/or video conferencing tools for audio only or occasional video use')
insert into item_questions (item_id, questions) values (18, 'Use of video conferencing with defined inclusivity guidelines')
insert into item_questions (item_id, questions) values (18, 'Team awareness of inclusive practices in the use of video conferencing combined with utlizing advanced communication features (e.g. voting, sketch pads)')
commit
