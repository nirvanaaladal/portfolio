
%original images dataset folder path 
raw_data_dir = 'C:\Users\na1802829\MATLAB Drive\dataset\tomato'; 

%destination to the preprocessed dataset folder
preprocessed_data_dir = 'C:\Users\na1802829\MATLAB Drive\dataset\tomato_updated';

%destination folder path
destination_folders = {preprocessed_data_dir}; 

%creating a full file path with the specified type
file_path = dir(fullfile(raw_data_dir, '*.jpg'));

%looping to preprocess each image in the dataset

for i = 1:numel(file_path)

    %creating a full file path to the image file in the current iteration
    filename = fullfile(raw_data_dir, file_path(i).name);

    fprintf('Reading the files %d of %d: %s\n', i, numel(file_path), filename);
    
    % reading the image
    image = imread(filename); 
    
    % applying the Gaussian filter
    img_filtered_gaussian = imgaussfilt(image, 3); 
 
    % converting to grayscale
    img_gray = rgb2gray(image); 

    % applying the histogram equalization
    img_eq = histeq(img_gray); 

    level = graythresh(img_eq);

    %applying the thresholding
    img_bw = imbinarize(img_eq, level);

 
    % saving the preprocessed images to the destination folder which is tomato_updated
    imwrite(img_filtered_gaussian, fullfile(destination_folders{1}, file_path(i).name));

    imwrite(img_eq, fullfile(destination_folders{1}, strcat('eq_', file_path(i).name)));

    imwrite(img_bw, fullfile(destination_folders{1}, strcat('bw_', file_path(i).name)));

end