insert into activity (id, name, icon, path, description) values (1, 'Assessment', 'CLIPBOARD_CHECK', 'assessment', 'The assessment section is used to estimate the degree to which software engineering practices are currently being used by your project. The table below shows your progress in each practice area. Click on the "Start Here" button to begin.');
insert into activity (id, name, icon, path, description) values (2, 'Tracking', 'TASKS', 'tracking', 'The tracking section is used to estimate the degree to which software engineering practices are currently being used by your project.');
insert into activity (id, name, icon, path, description) values (3, 'Integration', 'COGS', 'integration', 'The integration section is used to aid the integration of progress tracking cards into your development workflow.');
commit;
insert into score (id, name, color, activity_id) values (1, 'Basic', '#ff9933', 1);
insert into score (id, name, color, activity_id) values (2, 'Intermediate', '#99cc66', 1);
insert into score (id, name, color, activity_id) values (3, 'Advanced', '#066999', 1);
commit;
insert into category (id, name, activity_id, icon, path, description) values (1, 'Better Development', 1, '', 'development', 
  'Improving scientific software development—a process of writing, maintaining, and extending code—in order to increase software productivity, quality, and sustainability, as key aspects of advancing overall scientific productivity. The table below shows how well your team is doing for each practice. Click on the "Begin Assessment" button to start assessing your skills.');
insert into category (id, name, activity_id, icon, path, description) values (2, 'Better Planning', 1, '', 'planning', 
  'Improving strategies for planning in order to increase software productivity, quality, and sustainability, while underscoring the unique characteristics of scientific software, including that requirements often change during discovery. The table below shows how well your team is doing for each practice. Click on the "Begin Assessment" button to start assessing your skills.');
insert into category (id, name, activity_id, icon, path, description) values (3, 'Better Performance', 1, '', 'performance', 
  'Improving strategies for writing scientific software that is efficient, scalable, and portable—from laptops to emerging extreme-scale architectures—while preserving other code quality metrics such as correctness and usability. The table below shows how well your team is doing for each practice. Click on the "Begin Assessment" button to start assessing your skills');
insert into category (id, name, activity_id, icon, path, description) values (4, 'Better Reliability', 1, '', 'reliability', 
  'Improving methods of testing and verification to ensure that software is robust and produces reliable results, thereby helping to ensure the integrity of research and enable collaboration across teams. The table below shows how well your team is doing for each practice. Click on the "Begin Assessment" button to start assessing your skills.');
insert into category (id, name, activity_id, icon, path, description) values (5, 'Better Collaboration', 1, '', 'collaboration', 
  'Improving ways to facilitate work across teams and to promote partnerships and community contributions via software. We consider projects of all sizes, from small teams through multi-institutional aggregate teams. The table below shows how well your team is doing for each practice. Click on the "Begin Assessment" button to start assessing your skills.');
commit;
insert into item (id, name, category_id, icon, path, description) values (1, 'Revision Control', 1, '', 'revision_control', 
  'Approaches for managing changes to files (source code, documentation, data) as well as maintaining their history and attribution, especially across multiple contributors and branches of development (also known as version control).');
insert into item (id, name, category_id, icon, path, description) values (2, 'Code Reviews', 1, '', 'code_reviews', 
  'The act of consciously and systematically convening with one''s fellow programmers to check each other''s code for mistakes, and has been repeatedly shown to accelerate and streamline the process of software development like few other practices can.');
insert into item (id, name, category_id, icon, path, description) values (3, 'Issue Tracking', 1, '', 'issue_tracking', 
  'The process of capturing, reporting, tracking, and managing information about issues related to software. Issues include bugs, feature requests, missing documentation, and other problems and/or requirements.');
insert into item (id, name, category_id, icon, path, description) values (4, 'Deployment', 1, '', 'deployment', 
  'Approaches for versioning, packaging, releasing, and deploying software, documentation, or data for users to then obtain, install, and use.');
insert into item (id, name, category_id, icon, path, description) values (5, 'Documentation', 1, '', 'documentation', 
  'Creating, maintaining, and hosting quality documentation (written text or illustration) about the use, operation, or design of software.');
insert into item (id, name, category_id, icon, path, description) values (6, 'Development Process', 2, '', 'development_process', 
  'The software development life cycle is the process of dividing software development work into distinct phases to improve design, product management, and project management.');
insert into item (id, name, category_id, icon, path, description) values (7, 'Due Diligence', 2, '', 'due_diligence', 
  'Tracking and managing contributions to software.');
insert into item (id, name, category_id, icon, path, description) values (8, 'Software Design', 2, '', 'software_design', 
  'Major considerations in designing software to meet its scientific objectives and sustainability goals. Because requirements for scientific software often change during scientific discovery, design tends to evolve over time.');
insert into item (id, name, category_id, icon, path, description) values (9, 'Onboarding', 2, '', 'onboarding', 
  'The process of integrating a new employee into an organization or familiarizing a new customer or client with one''s products or services.');
insert into item (id, name, category_id, icon, path, description) values (10, 'Requirements Analysis', 2, '', 'requirements_analysis', 
  'Statements about what functions a software product shall perform, including any constraints under which it shall operate but avoiding as much as possible entanglements in how it shall be implemented.');
insert into item (id, name, category_id, icon, path, description) values (11, 'Testing', 4, '', 'testing', 
  'The process of ensuring that a software product is meeting its requirements at each stage of development, delivery, and deployment. Testing helps to avoid defects and verify correctness.');
insert into item (id, name, category_id, icon, path, description) values (12, 'Reproducibility', 4, '', 'reproducibility', 
  'Any effort whose goal is to increase trustworthiness and reuse of computational capabilities and results and to ensure correctness.');
commit;
insert into item_questions (item_id, questions) values (1, 'Uses revision control system such as Git');
insert into item_questions (item_id, questions) values (1, 'Uses a basic development workflow (e.g. basic Git workflow)');
insert into item_questions (item_id, questions) values (1, 'Uses an advanced development workflow (e.g. Gitflow)');
insert into item_questions (item_id, questions) values (2, 'Ad-hoc code reviews');
insert into item_questions (item_id, questions) values (2, 'Regular code reviews (e.g. weekly meetings)');
insert into item_questions (item_id, questions) values (2, 'Code reviews automated in workflow (e.g. via pull requests)');
insert into item_questions (item_id, questions) values (3, 'Manual issue tracking via email or other medium');
insert into item_questions (item_id, questions) values (3, 'Dedicated issue tracking system being used');
insert into item_questions (item_id, questions) values (3, 'Integrated issue tracking  (e.g. pull requests)');
insert into item_questions (item_id, questions) values (4, 'Manual deployment using a script');
insert into item_questions (item_id, questions) values (4, 'Deployment as part of development workflow with manual intervention');
insert into item_questions (item_id, questions) values (4, 'Continuous deployment');
insert into item_questions (item_id, questions) values (5, 'Ad-hoc text files');
insert into item_questions (item_id, questions) values (5, 'Code and documentation are cross referenced and updated when committed to repository');
insert into item_questions (item_id, questions) values (5, 'Integrated with the package release workflow');
insert into item_questions (item_id, questions) values (6, 'Has development process but it is based on ad-hoc rules');
insert into item_questions (item_id, questions) values (6, 'Employs an iterative development process');
insert into item_questions (item_id, questions) values (6, 'Uses an agile development methodology');
insert into item_questions (item_id, questions) values (7, 'Formal guidelines for accepting contributions');
insert into item_questions (item_id, questions) values (7, 'Clearly defined standards for coding and documentation');
insert into item_questions (item_id, questions) values (7, 'Provenance and license checking for contributions');
insert into item_questions (item_id, questions) values (8, 'Development guidelines include design in the process');
insert into item_questions (item_id, questions) values (8, 'A modeling language is employed for key aspects of the project');
insert into item_questions (item_id, questions) values (8, 'Visual modeling using a graphical representation to capture design');
insert into item_questions (item_id, questions) values (9, 'Initial onboarding process is documented');
insert into item_questions (item_id, questions) values (9, 'Used for supervisors for new hires');
insert into item_questions (item_id, questions) values (9, 'Used for all personnel changes');
insert into item_questions (item_id, questions) values (10, 'Development guidelines include requirements gathering');
insert into item_questions (item_id, questions) values (10, 'Formal requirements gathering is undertaken as part of the project');
insert into item_questions (item_id, questions) values (10, 'Requirements management process is employed');
insert into item_questions (item_id, questions) values (11, 'Comparison used to create system-level no-change tests');
insert into item_questions (item_id, questions) values (11, 'Unit testing for refactored and new code');
insert into item_questions (item_id, questions) values (11, 'Continuous integration');
insert into item_questions (item_id, questions) values (12, 'Publication of code');
insert into item_questions (item_id, questions) values (12, 'Inclusion of data when code is published');
insert into item_questions (item_id, questions) values (12, 'Automatic provenance capture system is employed');
commit;
