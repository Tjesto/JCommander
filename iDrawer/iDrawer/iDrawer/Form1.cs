using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO;
using System.Reflection;
using iDrawer.Plugins;
using System.Drawing.Imaging;
using System.Threading;

namespace iDrawer
{
    public partial class Form1 : Form, OnToolChangedListener, DrawingWindow
    {
        #region fields
        private List<BasePlugin> plugins = new List<BasePlugin>();
        private List<DrawingTool> tools = new List<DrawingTool>();
        private volatile Bitmap current;
        private Pen currentPen = Pens.Black;
        private Brush currentBrush = Brushes.Black;
        private Brush currentFillBrush = Brushes.Black;
        private Panel colorChooser;
        private DrawingTool currentTool;
        private bool isPainting;
        private int x;
        private int y;        
        delegate void SetTextCallback(string text); 
        delegate void SetImageCallback(Image image);
        delegate void SetProgressCallback(int progress);       
        private const int DEFAULT_WIDTH = 2;
        private const string SAVING = "Saving";
        private const string SAVED = "Saved";
        private const string OPENING = "Opening";
        private const string OPENED = "Opened";
        private const string EDITED = "Edited";
        private const string NEW = "New";
        private const string NEW_IMAGE = "New Image";
        private const string OP_IN_PROGRESS = "Operation in progress";
        private volatile bool shouldCloseAfterSave;
        #endregion
        public Form1()
        {
            InitializeComponent();
            initializeTools();
            initializePlugins();
            current = new Bitmap(panel2.ClientSize.Width, panel2.ClientSize.Height);
            Graphics.FromImage(current).Clear(panel2.BackColor);
            colorChooser = selectedColorPane;
        }

        private void initializeTools()
        {
            DirectoryInfo d = new DirectoryInfo(@".\Tools");
            FileInfo[] Files = d.GetFiles("*.dll");
            string str = "";
            foreach (FileInfo file in Files)
            {
                var ass = Assembly.LoadFrom(file.FullName);
                foreach (Type t in ass.GetTypes())
                {
                    if (t.IsClass && t.IsPublic && t.GetInterface("iDrawer.Plugins.DrawingTool") != null)
                    {
                        DrawingTool plugin = (DrawingTool)Activator.CreateInstance(t);
                        if (plugin != null)
                        {
                            tools.Add(plugin);
                            ToolStripItem item = plugin.getItem();
                            item.BackColor = SystemColors.ControlDark;
                            toolbar.Items.Add(item);
                            plugin.registerOnToolChangedListener(this);
                        }
                    }
                }
            }
            if (tools.Count > 0)
            {
                currentTool = tools[0];
                toolStripLabel1.Text = "Selected tool: " + currentTool.getName();
            }
        }

        private void initializePlugins()
        {
            DirectoryInfo d = new DirectoryInfo(@".\Plugins");
            FileInfo[] Files = d.GetFiles("*.dll");
            string str = "";            
            foreach (FileInfo file in Files)
            {
                var ass = Assembly.LoadFrom(file.FullName);
                foreach (Type t in ass.GetTypes())
                {
                    if (t.IsClass && t.IsPublic && t.GetInterface("iDrawer.Plugins.BasePlugin") != null)
                    {
                        BasePlugin plugin = (BasePlugin)Activator.CreateInstance(t);
                        if (plugin != null)
                        {
                            plugins.Add(plugin);
                            menu.Items.Add(plugin.createMenuItem());
                        }
                    }
                }
            }
        }

        private void saveToolStripMenuItem_Click(object sender, EventArgs e)
        {
            save();
        }

        private void panel2_Paint(object sender, PaintEventArgs e)
        {
            e.Graphics.DrawImage(current, Point.Empty);
        }
        #region colors choosing handling
        private void button1_Click(object sender, EventArgs e)
        {
            handleButtonClick(whiteBut);
        }

        private void handleButtonClick(Button color)
        {
            colorChooser.BackColor = color.BackColor;
        }

        private void button2_Click(object sender, EventArgs e)
        {
            handleButtonClick(blackBut);
        }

        private void button7_Click(object sender, EventArgs e)
        {
            handleButtonClick(brownBut);
        }

        private void greenBut_Click(object sender, EventArgs e)
        {
            handleButtonClick(greenBut);
        }

        private void blueBut_Click(object sender, EventArgs e)
        {
            handleButtonClick(blueBut);
        }

        private void yellowButton_Click(object sender, EventArgs e)
        {
            handleButtonClick(yellowButton);
        }

        private void orangeButton_Click(object sender, EventArgs e)
        {
            handleButtonClick(orangeButton);
        }

        private void redBut_Click(object sender, EventArgs e)
        {
            handleButtonClick(redBut);
        }

        private void violetBut_Click(object sender, EventArgs e)
        {
            handleButtonClick(violetBut);
        }

        private void purpleBut_Click(object sender, EventArgs e)
        {
            handleButtonClick(purpleBut);
        }

        private void selectedColorPane_BackColorChanged(object sender, EventArgs e)
        {
            Color newForeGroundColor = selectedColorPane.BackColor;
            currentPen = new Pen(newForeGroundColor);
            currentBrush = getBrushFor(newForeGroundColor);
        }

        private Brush getBrushFor(Color color)
        {            
            return new SolidBrush(color);
        }
        #endregion
        private void toolStripComboBox1_Click(object sender, EventArgs e)
        {
            //empty
        }

        public void notifyChanged(DrawingTool newTool)
        {
            currentTool = newTool;
            toolStripLabel1.Text = "Selected tool: " + currentTool.getName();
        }
        #region drawing
        private void panel2_MouseDown(object sender, MouseEventArgs e)
        {
            isPainting = true;
            x = e.X;
            y = e.Y;
            currentTool.onDrawStarted(currentPen, currentBrush, panel2.CreateGraphics(), x, y, e.X, e.Y, getSize(dotSizeBox.Text), currentFillBrush);
            currentTool.onDrawStarted(currentPen, currentBrush, Graphics.FromImage(current), x, y, e.X, e.Y, getSize(dotSizeBox.Text), currentFillBrush);
            setStatusLabel(EDITED);
        }

        private void panel2_MouseUp(object sender, MouseEventArgs e)
        {
            isPainting = false;
            currentTool.onDrawFinished(currentPen, currentBrush, panel2.CreateGraphics(), x, y, e.X, e.Y, getSize(dotSizeBox.Text), currentFillBrush);
            currentTool.onDrawFinished(currentPen, currentBrush, Graphics.FromImage(current), x, y, e.X, e.Y, getSize(dotSizeBox.Text), currentFillBrush);
        }

        private void panel2_MouseMove(object sender, MouseEventArgs e)
        {
            if (isPainting && currentTool != null && !toolStripLabel1.Text.Equals(SAVING) && !toolStripLabel1.Text.Equals(OPENING)) 
            {
                currentTool.draw(currentPen, currentBrush, panel2.CreateGraphics(), x, y, e.X, e.Y, getSize(dotSizeBox.Text), currentFillBrush);
                currentTool.draw(currentPen, currentBrush, Graphics.FromImage(current), x, y, e.X, e.Y, getSize(dotSizeBox.Text), currentFillBrush);
            }
        }

        private int getSize(string sizeText)
        {
            int firstNonNumber = 0;
            for (int i = 0; i < sizeText.Length; i++)
            {
                if (!Char.IsNumber(sizeText[i]))
                {
                    firstNonNumber = i;
                    break;
                }
            }
            if (firstNonNumber > 0)
            {
                string toParse = sizeText.Substring(0, firstNonNumber);
                return int.Parse(toParse);
            }
            return DEFAULT_WIDTH;
        }
        #endregion
        private void openMenuItem_Click(object sender, EventArgs e)
        {
            if (toolStripStatusLabel1.Text.Equals(EDITED))
            {
                if (editedImageAsked())
                {
                    return;
                }
            }            
            openFileDialog1.ShowDialog();
        }

        private void saveAsMenuItem_Click(object sender, EventArgs e)
        {
            panel2.Invalidate();
            saveFileDialog1.ShowDialog();            
        }

        private void openFileDialog1_FileOk(object sender, CancelEventArgs e)
        {
            new Thread(new ThreadStart(open)).Start();                          
        }

        private void saveFileDialog1_FileOk(object sender, CancelEventArgs e)
        {
            new Thread(new ThreadStart(saveAs)).Start();            
        }

        private void saveAs()
        {
            setStatusLabel(SAVING);
            setProgressValue(1 * 100 / 5);
            current.Save(saveFileDialog1.FileName, ImageFormat.Bmp);
            setProgressValue(3 * 100 / 5);
            setName(saveFileDialog1.FileName);
            setProgressValue(4 * 100 / 5);
            setStatusLabel(SAVED);
            setProgressValue(5 * 100 / 5);
            if (shouldCloseAfterSave)
            {
                realClose("");
            }
        }

        private void realClose(string empty)
        {
            if (this.InvokeRequired)
            {
                SetTextCallback d = new SetTextCallback(realClose);
                this.Invoke(d, new object[] {""});
            }
            else
            {
                this.Close();
            }
        }

        private void setStatusLabel(string label)
        {
            if (this.InvokeRequired)
            {
                SetTextCallback d = new SetTextCallback(setStatusLabel);
                this.Invoke(d, new object[] { label });
            }
            else
            {
                this.toolStripStatusLabel1.Text = label;
            }
        }

        private void setName(string newName)
        {
            if (this.InvokeRequired)
            {
                SetTextCallback d = new SetTextCallback(setName);
                this.Invoke(d, new object[] { newName });
            }
            else
            {
                this.Text = newName;
            }
        }

        private void putImage(Image image)
        {
            if (this.InvokeRequired)
            {
                SetImageCallback d = new SetImageCallback(putImage);
                this.Invoke(d, new object[] { image });
            }
            else
            {
                panel2.BackgroundImage = image;
            }
        }

        private void open()
        {
            setStatusLabel(OPENING);
            setProgressValue(1 * 100 / 7);
            Image bitmap = Image.FromFile(openFileDialog1.FileName);
            setProgressValue(3 * 100 / 7);
            putImage(bitmap);
            setProgressValue(4 * 100 / 7);
            current = (Bitmap)bitmap;
            setProgressValue(5 * 100 / 7);
            setName(openFileDialog1.FileName);
            setProgressValue(6 * 100 / 7);
            setStatusLabel(OPENED);
            setProgressValue(7 * 100 / 7);
        }



        private void statusbar_ItemClicked(object sender, ToolStripItemClickedEventArgs e)
        {
            //empty
        }

        private void newMenuItem_Click(object sender, EventArgs e)
        {            
            if (!toolStripStatusLabel1.Text.Equals(EDITED))
            {
                return;
            }
            if (editedImageAsked())
            {
                return;
            }
            panel2.BackColor = Color.White;
            Graphics.FromImage(current).Clear(Color.White);
            panel2.Invalidate();
            setName(NEW_IMAGE);
        }

        private bool editedImageAsked()
        {
            DialogResult r = MessageBox.Show("Image was edited, do you want to save it?", "Do you want to save you image?", MessageBoxButtons.YesNoCancel, MessageBoxIcon.Question, MessageBoxDefaultButton.Button1);
            if (r == DialogResult.Cancel)
            {
                return true;
            }
            if (r == DialogResult.Yes)
            {
                save();
            }
            return false;
        }

        private DialogResult rEditedImageAsked()
        {
            DialogResult r = MessageBox.Show("Image was edited, do you want to save it?", "Do you want to save you image?", MessageBoxButtons.YesNoCancel, MessageBoxIcon.Question, MessageBoxDefaultButton.Button1);
            if (r == DialogResult.Cancel)
            {
                return r;
            }
            if (r == DialogResult.Yes)
            {
                save();
            }
            return r;
        }

        private void save()
        {
            if (!toolStripStatusLabel1.Text.Equals(EDITED))
            {
                return;
            }
            panel2.Invalidate();
            if (Text.Equals(NEW_IMAGE))
            {
                saveFileDialog1.ShowDialog();
            }
            else
            {
                new Thread(new ParameterizedThreadStart(simpleSave)).Start(Text);
            }

        }

        private void simpleSave(object oName) 
        {
            string name = (string)oName;
            setProgressValue((1 * 100) / 6);
            setStatusLabel(SAVING);
            setProgressValue((2 * 100) / 6);
            File.Delete(name);
            setProgressValue((3 * 100) / 6);
            current.Save(name, ImageFormat.Bmp);
            setProgressValue((5 * 100) / 6);
            setStatusLabel(SAVED);
            setProgressValue((6 * 100) / 6);
            if (shouldCloseAfterSave)
            {
                realClose("");
            }
        }

        private void setProgressValue(int val)
        {
            if (this.InvokeRequired)
            {
                SetProgressCallback d = new SetProgressCallback(setProgressValue);
                this.Invoke(d, new object[] { val });
            }
            else
            {
                operationProgress.Value = val;
            }
        }

        private void Form1_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (closingLocked)
            {
                e.Cancel = true;
                return;
            }
            if (toolStripStatusLabel1.Text.Equals(EDITED))
            {
                DialogResult r = rEditedImageAsked();
                if (r == DialogResult.Cancel)
                {
                    e.Cancel = true;
                }
                if (r == DialogResult.Yes)
                {
                    e.Cancel = true;
                    shouldCloseAfterSave = true;
                }
            }
        }

        private void selectedColorPane_Click(object sender, EventArgs e)
        {
            colorChooser = selectedColorPane;
        }

        private void fillColorPane_Click(object sender, EventArgs e)
        {
            colorChooser = fillColorPane;
        }

        private void fillColorPane_BackColorChanged(object sender, EventArgs e)
        {
            currentFillBrush = new SolidBrush(fillColorPane.BackColor);
        }

        #region API for plugins
        private bool closingLocked = false;
        public void updateProgress(int newValue)
        {
            setProgressValue(newValue);
        }
        public Bitmap getCurrentBitmap()
        {
            return current;
        }
        public void updateDrawingBoard(Bitmap newBitmap)
        {
            panel2.BackgroundImage = newBitmap;
            current = newBitmap;
            panel2.Invalidate();
            setStatusLabel(EDITED);
        }
        public void lockClosing()
        {
            closingLocked = true;
            setOperationInProgress(true);
        }
        public void unlockClosing()
        {
            closingLocked = false;
            setOperationInProgress(false);
        }
        public void setOperationInProgress(bool justStarted)
        {
            setStatusLabel(justStarted ? OP_IN_PROGRESS : EDITED);
        }
        #endregion
    }
}
