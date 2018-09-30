package raid.study.photofilters.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class UIDialogs {
  private final JFrame parent;
  private final FileFilter imageInputFileFilter;
  private final FileFilter imageOutputFileFilter;

  public UIDialogs(JFrame parent) {
    this.parent = parent;

    String[] imageInputExtensions = ImageIO.getReaderFileSuffixes();
    String filterName = String.format("Images (%s)", String.join(", ", imageInputExtensions));
    imageInputFileFilter = new FileNameExtensionFilter(filterName, imageInputExtensions);

    String[] imageOutputExtensions = ImageIO.getWriterFileSuffixes();
    filterName = String.format("Images (%s)", String.join(", ", imageOutputExtensions));
    imageOutputFileFilter = new FileNameExtensionFilter(filterName, imageOutputExtensions);
  }

//  public int closeUnsavedConfirmationDialog() {
//    return JOptionPane.showConfirmDialog(parent, UIConstants.CLOSE_UNSAVED_MESSAGE, UIConstants.CLOSE_UNSAVED_TITLE,
//        JOptionPane.YES_NO_CANCEL_OPTION);
//  }

  public void errorDialog(String errorMessage) {
    JOptionPane.showMessageDialog(parent, errorMessage, UIConstants.ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
  }

  public File openFileDialog() {
    return fileChooserDialog("Open", JFileChooser.OPEN_DIALOG, imageInputFileFilter);
  }

  public File saveFileDialog() {
    return fileChooserDialog("Save", JFileChooser.SAVE_DIALOG, imageOutputFileFilter);
  }

  private File fileChooserDialog(String buttonText, int dialogType, FileFilter fileFilter) {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogType(dialogType);
    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    for (FileFilter presetFilter : fileChooser.getChoosableFileFilters()) {
      fileChooser.removeChoosableFileFilter(presetFilter);
    }
    fileChooser.addChoosableFileFilter(fileFilter);
    int result = fileChooser.showDialog(parent, buttonText);
    if (result != JFileChooser.APPROVE_OPTION)
      return null;
    return fileChooser.getSelectedFile();
  }
}
