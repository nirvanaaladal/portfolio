% Set the directory for the raw dataset

raw_data_dir = 'C:\Users\na1802829\Documents\MATLAB\dataset\tomato';

 

% Set the directory for the preprocessed dataset

preprocessed_data_dir = 'C:\Users\na1802829\MATLAB Drive\dataset\tomato_updated';

 

% Create preprocessed directory if it does not exist

if ~exist(preprocessed_data_dir, 'dir')

    mkdir(preprocessed_data_dir);

end

 

files = dir(fullfile(raw_data_dir, '*.jpg'));

 

for i = 1:numel(files)

 

    filename = fullfile(raw_data_dir, files(i).name);

 

    fprintf('Reading file %d of %d: %s\n', i, numel(files), filename);

 

    img = imread(filename); % read in the image

    img = imresize(img, [256 256]); % Resize to 256x256 pixels

    img_gray = rgb2gray(img); % convert RGB image to grayscale



      
   % img = imread(filename); % read in the image

 

    img_filtered_gaussian = imgaussfilt(img, 3); % apply Gaussian filter

 

    img_filtered_median = medfilt2(img,[2 2]); % apply median filter

 

    img_eq = histeq(img_filtered_gaussian); % perform histogram equalization

 

    img_corrected_gamma = imadjust(img_filtered_gaussian,[],[],0.5); % perform gamma correction

 

    img_resized = imresize(img_corrected_gamma, [256, 256]); % resize the image

 

    img_gray = rgb2gray(img_resized); % convert the image to grayscale

 

    imwrite(img_gray, fullfile(destination_folders{1}, files(i).name)); % save the preprocessed images

 
 

    % Save the preprocessed images

   % imwrite(img_filtered_gaussian, fullfile(preprocessed_data_dir, files(i).name));

 

end