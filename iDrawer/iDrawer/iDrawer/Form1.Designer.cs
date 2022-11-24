namespace iDrawer
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Form1));
            this.statusbar = new System.Windows.Forms.StatusStrip();
            this.operationProgress = new System.Windows.Forms.ToolStripProgressBar();
            this.toolStripStatusLabel1 = new System.Windows.Forms.ToolStripStatusLabel();
            this.menu = new System.Windows.Forms.MenuStrip();
            this.fileSubMenu = new System.Windows.Forms.ToolStripMenuItem();
            this.newMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.openMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.saveMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.saveAsMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.toolbar = new System.Windows.Forms.ToolStrip();
            this.dotSizeBox = new System.Windows.Forms.ToolStripComboBox();
            this.toolStripLabel1 = new System.Windows.Forms.ToolStripLabel();
            this.panel1 = new System.Windows.Forms.Panel();
            this.fillColorPane = new System.Windows.Forms.Panel();
            this.label2 = new System.Windows.Forms.Label();
            this.purpleBut = new System.Windows.Forms.Button();
            this.violetBut = new System.Windows.Forms.Button();
            this.selectedColorPane = new System.Windows.Forms.Panel();
            this.label1 = new System.Windows.Forms.Label();
            this.brownBut = new System.Windows.Forms.Button();
            this.redBut = new System.Windows.Forms.Button();
            this.orangeButton = new System.Windows.Forms.Button();
            this.yellowButton = new System.Windows.Forms.Button();
            this.blueBut = new System.Windows.Forms.Button();
            this.greenBut = new System.Windows.Forms.Button();
            this.blackBut = new System.Windows.Forms.Button();
            this.whiteBut = new System.Windows.Forms.Button();
            this.panel2 = new System.Windows.Forms.Panel();
            this.saveFileDialog1 = new System.Windows.Forms.SaveFileDialog();
            this.openFileDialog1 = new System.Windows.Forms.OpenFileDialog();
            this.toolStripSeparator1 = new System.Windows.Forms.ToolStripSeparator();
            this.redoBut = new System.Windows.Forms.ToolStripButton();
            this.undoBut = new System.Windows.Forms.ToolStripButton();
            this.statusbar.SuspendLayout();
            this.menu.SuspendLayout();
            this.toolbar.SuspendLayout();
            this.panel1.SuspendLayout();
            this.SuspendLayout();
            // 
            // statusbar
            // 
            this.statusbar.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.operationProgress,
            this.toolStripStatusLabel1});
            this.statusbar.Location = new System.Drawing.Point(0, 570);
            this.statusbar.Name = "statusbar";
            this.statusbar.Size = new System.Drawing.Size(917, 22);
            this.statusbar.TabIndex = 0;
            this.statusbar.Text = "statusStrip1";
            this.statusbar.ItemClicked += new System.Windows.Forms.ToolStripItemClickedEventHandler(this.statusbar_ItemClicked);
            // 
            // operationProgress
            // 
            this.operationProgress.Name = "operationProgress";
            this.operationProgress.Size = new System.Drawing.Size(100, 16);
            // 
            // toolStripStatusLabel1
            // 
            this.toolStripStatusLabel1.Name = "toolStripStatusLabel1";
            this.toolStripStatusLabel1.Size = new System.Drawing.Size(31, 17);
            this.toolStripStatusLabel1.Text = "New";
            // 
            // menu
            // 
            this.menu.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.fileSubMenu});
            this.menu.Location = new System.Drawing.Point(0, 0);
            this.menu.Name = "menu";
            this.menu.Size = new System.Drawing.Size(917, 24);
            this.menu.TabIndex = 1;
            this.menu.Text = "menuStrip1";
            // 
            // fileSubMenu
            // 
            this.fileSubMenu.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.newMenuItem,
            this.openMenuItem,
            this.saveMenuItem,
            this.saveAsMenuItem});
            this.fileSubMenu.Name = "fileSubMenu";
            this.fileSubMenu.Size = new System.Drawing.Size(37, 20);
            this.fileSubMenu.Text = "File";
            // 
            // newMenuItem
            // 
            this.newMenuItem.Name = "newMenuItem";
            this.newMenuItem.Size = new System.Drawing.Size(193, 22);
            this.newMenuItem.Text = "New";
            this.newMenuItem.Click += new System.EventHandler(this.newMenuItem_Click);
            // 
            // openMenuItem
            // 
            this.openMenuItem.Name = "openMenuItem";
            this.openMenuItem.ShortcutKeys = ((System.Windows.Forms.Keys)((System.Windows.Forms.Keys.Control | System.Windows.Forms.Keys.O)));
            this.openMenuItem.Size = new System.Drawing.Size(193, 22);
            this.openMenuItem.Text = "Open";
            this.openMenuItem.Click += new System.EventHandler(this.openMenuItem_Click);
            // 
            // saveMenuItem
            // 
            this.saveMenuItem.Name = "saveMenuItem";
            this.saveMenuItem.ShortcutKeys = ((System.Windows.Forms.Keys)((System.Windows.Forms.Keys.Control | System.Windows.Forms.Keys.S)));
            this.saveMenuItem.Size = new System.Drawing.Size(193, 22);
            this.saveMenuItem.Text = "Save";
            this.saveMenuItem.Click += new System.EventHandler(this.saveToolStripMenuItem_Click);
            // 
            // saveAsMenuItem
            // 
            this.saveAsMenuItem.Name = "saveAsMenuItem";
            this.saveAsMenuItem.ShortcutKeys = ((System.Windows.Forms.Keys)(((System.Windows.Forms.Keys.Control | System.Windows.Forms.Keys.Shift) 
            | System.Windows.Forms.Keys.S)));
            this.saveAsMenuItem.Size = new System.Drawing.Size(193, 22);
            this.saveAsMenuItem.Text = "Save as...";
            this.saveAsMenuItem.Click += new System.EventHandler(this.saveAsMenuItem_Click);
            // 
            // toolbar
            // 
            this.toolbar.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.dotSizeBox,
            this.undoBut,
            this.redoBut,
            this.toolStripLabel1,
            this.toolStripSeparator1});
            this.toolbar.Location = new System.Drawing.Point(0, 24);
            this.toolbar.Name = "toolbar";
            this.toolbar.Size = new System.Drawing.Size(917, 25);
            this.toolbar.TabIndex = 2;
            this.toolbar.Text = "toolStrip1";
            // 
            // dotSizeBox
            // 
            this.dotSizeBox.Items.AddRange(new object[] {
            "1 px",
            "2 px",
            "5 px",
            "10 px",
            "20 px"});
            this.dotSizeBox.Name = "dotSizeBox";
            this.dotSizeBox.Size = new System.Drawing.Size(121, 25);
            this.dotSizeBox.Text = "2 px";
            this.dotSizeBox.Click += new System.EventHandler(this.toolStripComboBox1_Click);
            // 
            // toolStripLabel1
            // 
            this.toolStripLabel1.Name = "toolStripLabel1";
            this.toolStripLabel1.Size = new System.Drawing.Size(81, 22);
            this.toolStripLabel1.Text = "Selected tool: ";
            // 
            // panel1
            // 
            this.panel1.Controls.Add(this.fillColorPane);
            this.panel1.Controls.Add(this.label2);
            this.panel1.Controls.Add(this.purpleBut);
            this.panel1.Controls.Add(this.violetBut);
            this.panel1.Controls.Add(this.selectedColorPane);
            this.panel1.Controls.Add(this.label1);
            this.panel1.Controls.Add(this.brownBut);
            this.panel1.Controls.Add(this.redBut);
            this.panel1.Controls.Add(this.orangeButton);
            this.panel1.Controls.Add(this.yellowButton);
            this.panel1.Controls.Add(this.blueBut);
            this.panel1.Controls.Add(this.greenBut);
            this.panel1.Controls.Add(this.blackBut);
            this.panel1.Controls.Add(this.whiteBut);
            this.panel1.Location = new System.Drawing.Point(12, 52);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(62, 515);
            this.panel1.TabIndex = 3;
            // 
            // fillColorPane
            // 
            this.fillColorPane.BackColor = System.Drawing.Color.Black;
            this.fillColorPane.Location = new System.Drawing.Point(6, 232);
            this.fillColorPane.Name = "fillColorPane";
            this.fillColorPane.Size = new System.Drawing.Size(49, 48);
            this.fillColorPane.TabIndex = 1;
            this.fillColorPane.BackColorChanged += new System.EventHandler(this.fillColorPane_BackColorChanged);
            this.fillColorPane.Click += new System.EventHandler(this.fillColorPane_Click);
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(3, 216);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(46, 13);
            this.label2.TabIndex = 2;
            this.label2.Text = "Fill Color";
            // 
            // purpleBut
            // 
            this.purpleBut.BackColor = System.Drawing.Color.Purple;
            this.purpleBut.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.purpleBut.Location = new System.Drawing.Point(32, 119);
            this.purpleBut.Name = "purpleBut";
            this.purpleBut.Size = new System.Drawing.Size(23, 23);
            this.purpleBut.TabIndex = 9;
            this.purpleBut.UseVisualStyleBackColor = false;
            this.purpleBut.Click += new System.EventHandler(this.purpleBut_Click);
            // 
            // violetBut
            // 
            this.violetBut.BackColor = System.Drawing.Color.Violet;
            this.violetBut.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.violetBut.Location = new System.Drawing.Point(3, 119);
            this.violetBut.Name = "violetBut";
            this.violetBut.Size = new System.Drawing.Size(23, 23);
            this.violetBut.TabIndex = 8;
            this.violetBut.UseVisualStyleBackColor = false;
            this.violetBut.Click += new System.EventHandler(this.violetBut_Click);
            // 
            // selectedColorPane
            // 
            this.selectedColorPane.BackColor = System.Drawing.Color.Black;
            this.selectedColorPane.Location = new System.Drawing.Point(6, 161);
            this.selectedColorPane.Name = "selectedColorPane";
            this.selectedColorPane.Size = new System.Drawing.Size(49, 48);
            this.selectedColorPane.TabIndex = 0;
            this.selectedColorPane.BackColorChanged += new System.EventHandler(this.selectedColorPane_BackColorChanged);
            this.selectedColorPane.Click += new System.EventHandler(this.selectedColorPane_Click);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(3, 145);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(31, 13);
            this.label1.TabIndex = 0;
            this.label1.Text = "Color";
            // 
            // brownBut
            // 
            this.brownBut.BackColor = System.Drawing.Color.Brown;
            this.brownBut.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.brownBut.Location = new System.Drawing.Point(32, 90);
            this.brownBut.Name = "brownBut";
            this.brownBut.Size = new System.Drawing.Size(23, 23);
            this.brownBut.TabIndex = 7;
            this.brownBut.UseVisualStyleBackColor = false;
            this.brownBut.Click += new System.EventHandler(this.button7_Click);
            // 
            // redBut
            // 
            this.redBut.BackColor = System.Drawing.Color.Red;
            this.redBut.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.redBut.Location = new System.Drawing.Point(3, 90);
            this.redBut.Name = "redBut";
            this.redBut.Size = new System.Drawing.Size(23, 23);
            this.redBut.TabIndex = 6;
            this.redBut.UseVisualStyleBackColor = false;
            this.redBut.Click += new System.EventHandler(this.redBut_Click);
            // 
            // orangeButton
            // 
            this.orangeButton.BackColor = System.Drawing.Color.Orange;
            this.orangeButton.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.orangeButton.Location = new System.Drawing.Point(32, 61);
            this.orangeButton.Name = "orangeButton";
            this.orangeButton.Size = new System.Drawing.Size(23, 23);
            this.orangeButton.TabIndex = 5;
            this.orangeButton.UseVisualStyleBackColor = false;
            this.orangeButton.Click += new System.EventHandler(this.orangeButton_Click);
            // 
            // yellowButton
            // 
            this.yellowButton.BackColor = System.Drawing.Color.Yellow;
            this.yellowButton.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.yellowButton.Location = new System.Drawing.Point(3, 61);
            this.yellowButton.Name = "yellowButton";
            this.yellowButton.Size = new System.Drawing.Size(23, 23);
            this.yellowButton.TabIndex = 4;
            this.yellowButton.UseVisualStyleBackColor = false;
            this.yellowButton.Click += new System.EventHandler(this.yellowButton_Click);
            // 
            // blueBut
            // 
            this.blueBut.BackColor = System.Drawing.Color.Blue;
            this.blueBut.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.blueBut.Location = new System.Drawing.Point(32, 32);
            this.blueBut.Name = "blueBut";
            this.blueBut.Size = new System.Drawing.Size(23, 23);
            this.blueBut.TabIndex = 3;
            this.blueBut.UseVisualStyleBackColor = false;
            this.blueBut.Click += new System.EventHandler(this.blueBut_Click);
            // 
            // greenBut
            // 
            this.greenBut.BackColor = System.Drawing.Color.Green;
            this.greenBut.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.greenBut.Location = new System.Drawing.Point(3, 32);
            this.greenBut.Name = "greenBut";
            this.greenBut.Size = new System.Drawing.Size(23, 23);
            this.greenBut.TabIndex = 2;
            this.greenBut.UseVisualStyleBackColor = false;
            this.greenBut.Click += new System.EventHandler(this.greenBut_Click);
            // 
            // blackBut
            // 
            this.blackBut.BackColor = System.Drawing.Color.Black;
            this.blackBut.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.blackBut.Location = new System.Drawing.Point(32, 3);
            this.blackBut.Name = "blackBut";
            this.blackBut.Size = new System.Drawing.Size(23, 23);
            this.blackBut.TabIndex = 1;
            this.blackBut.UseVisualStyleBackColor = false;
            this.blackBut.Click += new System.EventHandler(this.button2_Click);
            // 
            // whiteBut
            // 
            this.whiteBut.BackColor = System.Drawing.Color.White;
            this.whiteBut.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.whiteBut.Location = new System.Drawing.Point(3, 3);
            this.whiteBut.Name = "whiteBut";
            this.whiteBut.Size = new System.Drawing.Size(23, 23);
            this.whiteBut.TabIndex = 0;
            this.whiteBut.UseVisualStyleBackColor = false;
            this.whiteBut.Click += new System.EventHandler(this.button1_Click);
            // 
            // panel2
            // 
            this.panel2.BackColor = System.Drawing.Color.White;
            this.panel2.Location = new System.Drawing.Point(80, 52);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(825, 515);
            this.panel2.TabIndex = 4;
            this.panel2.Paint += new System.Windows.Forms.PaintEventHandler(this.panel2_Paint);
            this.panel2.MouseDown += new System.Windows.Forms.MouseEventHandler(this.panel2_MouseDown);
            this.panel2.MouseMove += new System.Windows.Forms.MouseEventHandler(this.panel2_MouseMove);
            this.panel2.MouseUp += new System.Windows.Forms.MouseEventHandler(this.panel2_MouseUp);
            // 
            // saveFileDialog1
            // 
            this.saveFileDialog1.DefaultExt = "bmp";
            this.saveFileDialog1.FileOk += new System.ComponentModel.CancelEventHandler(this.saveFileDialog1_FileOk);
            // 
            // openFileDialog1
            // 
            this.openFileDialog1.DefaultExt = "bmp";
            this.openFileDialog1.FileName = "openFileDialog1";
            this.openFileDialog1.FileOk += new System.ComponentModel.CancelEventHandler(this.openFileDialog1_FileOk);
            // 
            // toolStripSeparator1
            // 
            this.toolStripSeparator1.Name = "toolStripSeparator1";
            this.toolStripSeparator1.Size = new System.Drawing.Size(6, 25);
            // 
            // redoBut
            // 
            this.redoBut.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Image;
            this.redoBut.Enabled = false;
            this.redoBut.Image = ((System.Drawing.Image)(resources.GetObject("redoBut.Image")));
            this.redoBut.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.redoBut.Name = "redoBut";
            this.redoBut.Size = new System.Drawing.Size(23, 22);
            this.redoBut.Text = "Redo";
            this.redoBut.Click += new System.EventHandler(this.redoBut_Click);
            // 
            // undoBut
            // 
            this.undoBut.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Image;
            this.undoBut.Enabled = false;
            this.undoBut.Image = ((System.Drawing.Image)(resources.GetObject("undoBut.Image")));
            this.undoBut.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.undoBut.Name = "undoBut";
            this.undoBut.Size = new System.Drawing.Size(23, 22);
            this.undoBut.Text = "Undo";
            this.undoBut.Click += new System.EventHandler(this.undoBut_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(917, 592);
            this.Controls.Add(this.panel2);
            this.Controls.Add(this.panel1);
            this.Controls.Add(this.toolbar);
            this.Controls.Add(this.statusbar);
            this.Controls.Add(this.menu);
            this.MainMenuStrip = this.menu;
            this.Name = "Form1";
            this.Text = "New Image";
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.Form1_FormClosing);
            this.statusbar.ResumeLayout(false);
            this.statusbar.PerformLayout();
            this.menu.ResumeLayout(false);
            this.menu.PerformLayout();
            this.toolbar.ResumeLayout(false);
            this.toolbar.PerformLayout();
            this.panel1.ResumeLayout(false);
            this.panel1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.StatusStrip statusbar;
        private System.Windows.Forms.MenuStrip menu;
        private System.Windows.Forms.ToolStrip toolbar;
        private System.Windows.Forms.ToolStripMenuItem fileSubMenu;
        private System.Windows.Forms.ToolStripMenuItem newMenuItem;
        private System.Windows.Forms.ToolStripMenuItem openMenuItem;
        private System.Windows.Forms.ToolStripMenuItem saveMenuItem;
        private System.Windows.Forms.ToolStripMenuItem saveAsMenuItem;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.Panel panel2;
        private System.Windows.Forms.Button whiteBut;
        private System.Windows.Forms.Button blackBut;
        private System.Windows.Forms.Panel selectedColorPane;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Button brownBut;
        private System.Windows.Forms.Button redBut;
        private System.Windows.Forms.Button orangeButton;
        private System.Windows.Forms.Button yellowButton;
        private System.Windows.Forms.Button blueBut;
        private System.Windows.Forms.Button greenBut;
        private System.Windows.Forms.Button purpleBut;
        private System.Windows.Forms.Button violetBut;
        private System.Windows.Forms.ToolStripComboBox dotSizeBox;
        private System.Windows.Forms.ToolStripLabel toolStripLabel1;
        private System.Windows.Forms.SaveFileDialog saveFileDialog1;
        private System.Windows.Forms.OpenFileDialog openFileDialog1;
        private System.Windows.Forms.ToolStripProgressBar operationProgress;
        private System.Windows.Forms.ToolStripStatusLabel toolStripStatusLabel1;
        private System.Windows.Forms.Panel fillColorPane;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.ToolStripButton undoBut;
        private System.Windows.Forms.ToolStripButton redoBut;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator1;
    }
}

