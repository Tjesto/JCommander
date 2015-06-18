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
    public partial class Form1 : Form, OnToolChangedListener
    {
        private List<BasePlugin> plugins = new List<BasePlugin>();
        private List<DrawingTool> tools = new List<DrawingTool>();
        private volatile Bitmap current;
        private Pen currentPen = Pens.Black;
        private Brush currentBrush = Brushes.Black;
        private DrawingTool currentTool;
        private bool isPainting;
        private int x;
        private int y;        
        delegate void SetTextCallback(string text); 
        delegate void SetImageCallback(Image image);
        private const int DEFAULT_WIDTH = 2;
        private const string SAVING = "Saving";
        private const string SAVED = "Saved";
        private const string OPENING = "Opening";
        private const string OPENED = "Opened";
        private const string EDITED = "Edited";
        private const string NEW = "New";        

        public Form1()
        {
            InitializeComponent();
            initializeTools();
            initializePlugins();
            current = new Bitmap(panel2.ClientSize.Width, panel2.ClientSize.Height);
            Graphics.FromImage(current).Clear(panel2.BackColor);
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
                            toolbar.Items.Add(plugin.getItem());
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

        }

        private void panel2_Paint(object sender, PaintEventArgs e)
        {
            e.Graphics.DrawImage(current, Point.Empty);
        }

        private void button1_Click(object sender, EventArgs e)
        {
            handleButtonClick(whiteBut);
        }

        private void handleButtonClick(Button color)
        {
            selectedColorPane.BackColor = color.BackColor;
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

        private void toolStripComboBox1_Click(object sender, EventArgs e)
        {
            save();
        }

        public void notifyChanged(DrawingTool newTool)
        {
            currentTool = newTool;
            toolStripLabel1.Text = "Selected tool: " + currentTool.getName();
        }

        private void panel2_MouseDown(object sender, MouseEventArgs e)
        {
            isPainting = true;
            currentTool.onDrawStarted(currentPen, currentBrush, panel2.CreateGraphics(), x, y, e.X, e.Y, getSize(dotSizeBox.Text));
            x = e.X;
            y = e.Y;
            setStatusLabel(EDITED);
        }

        private void panel2_MouseUp(object sender, MouseEventArgs e)
        {
            isPainting = false;
            currentTool.onDrawFinished(currentPen, currentBrush, panel2.CreateGraphics(), x, y, e.X, e.Y, getSize(dotSizeBox.Text));
        }

        private void panel2_MouseMove(object sender, MouseEventArgs e)
        {
            if (isPainting && currentTool != null) 
            {
                currentTool.draw(currentPen, currentBrush, panel2.CreateGraphics(), x, y, e.X, e.Y, getSize(dotSizeBox.Text));
                currentTool.draw(currentPen, currentBrush, Graphics.FromImage(current), x, y, e.X, e.Y, getSize(dotSizeBox.Text));
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

        private void openMenuItem_Click(object sender, EventArgs e)
        {
            if (!toolStripStatusLabel1.Text.Equals(EDITED))
            {
                return;
            }
            if (editedImageAsked())
            {
                return;
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
            current.Save(saveFileDialog1.FileName, ImageFormat.Bmp);
            setName(saveFileDialog1.FileName);
            setStatusLabel(SAVED);
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
            Image bitmap = Image.FromFile(openFileDialog1.FileName);
            putImage(bitmap);
            current = (Bitmap)bitmap;
            setName(openFileDialog1.FileName);
            setStatusLabel(OPENED);
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

        private void save()
        {

        }

        private void Form1_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (toolStripStatusLabel1.Text.Equals(EDITED) && editedImageAsked())
            {
                e.Cancel = true;
            }            
        }
    }
}
